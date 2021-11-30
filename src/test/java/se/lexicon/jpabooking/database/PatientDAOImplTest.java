package se.lexicon.jpabooking.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.entity.Patient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
class PatientDAOImplTest {

    public static final String PNR = "199001011212";
    public static final String FIRST_NAME = "Ola";
    public static final String LAST_NAME = "Olsson";
    public static final LocalDate BIRTH_DATE = LocalDate.parse("1990-01-01");
    @Autowired
    private PatientDAOImpl testObject;
    @Autowired
    private TestEntityManager em;

    public List<Patient> patients(){
        return Arrays.asList(
                new Patient(null, PNR, FIRST_NAME, LAST_NAME, BIRTH_DATE),
                new Patient(null, "199001011213", "Olga", "Olsson", LocalDate.parse("1990-01-01")),
                new Patient(null, "199801014575", "Anna", "Alfredsson", LocalDate.parse("1998-01-01"))
        );
    }

    private List<Patient> persistedPatients;


    @BeforeEach
    void setUp() {
        persistedPatients = patients().stream()
                .map(em::persist)
                .collect(Collectors.toList());
    }

    @Test
    void save_persist() {
        Patient patient = new Patient(null, "198304123465", "Nils", "Svensson", LocalDate.parse("1983-04-12"));
        Patient result = testObject.save(patient);

        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void save_merge() {
        Patient patient = em.persist(new Patient(null, "198304123465", "Nils", "Svensson", LocalDate.parse("1983-04-12")));
        patient.setLastName("Nilsson");
        Patient result = testObject.save(patient);

        assertNotNull(result);
        assertEquals("Nilsson", result.getLastName());
    }

    @Test
    void save_throws_IllegalArgumentException() {
        assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> testObject.save(null)
        );
    }

    @Test
    void findById() {
        String id = persistedPatients.get(0).getId();

        Optional<Patient> result = testObject.findById(id);
        assertTrue(result.isPresent());
        Patient patient = result.get();
        assertEquals(id, patient.getId());
        assertEquals(FIRST_NAME, patient.getFirstName());
        assertEquals(LAST_NAME, patient.getLastName());
        assertEquals(PNR, patient.getPnr());
        assertEquals(BIRTH_DATE, patient.getBirthDate());
    }

    @Test
    void findByPnr() {
        String pnr = "1990 01 01-1212";
        Optional<Patient> result = testObject.findByPnr(pnr);
        assertTrue(result.isPresent());
    }

    @Test
    void findAll() {
        int expected = 3;
        List<Patient> result = testObject.findAll();

        assertEquals(expected, result.size());
    }

    @Test
    void searchByName() {
        String searchName = "ol";
        int expected = 2;

        List<Patient> result = testObject.searchByName(searchName);

        assertEquals(expected, result.size());
    }

    @Test
    void delete() {
        String id = persistedPatients.get(0).getId();
        testObject.delete(id);

        assertNull(em.find(Patient.class, id));
    }
}