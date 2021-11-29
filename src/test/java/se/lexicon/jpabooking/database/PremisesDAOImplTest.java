package se.lexicon.jpabooking.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.Premises;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class PremisesDAOImplTest {

    public static final String NAME = "Norr";
    @Autowired
    private PremisesDAOImpl testObject;
    @Autowired
    private TestEntityManager em;

    public List<Premises> premises(){
        return Arrays.asList(
                new Premises(null, NAME),
                new Premises(null, "Söder"),
                new Premises(null, "Väster"),
                new Premises(null, "Öster")
        );
    }

    private Premises premises;

    @BeforeEach
    void setUp() {
        List<Premises> persistedPremises = premises().stream()
                .map(em::persist)
                .collect(Collectors.toList());
        premises = persistedPremises.get(0);
    }

    @Test
    void save_persist() {
        Premises premises = new Premises(null, "Centrum");
        Premises result = testObject.save(premises);

        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void save_update() {
        Premises premises = this.premises;
        premises.setName("Vårdcentral Norr");

        Premises result = testObject.save(premises);
        assertNotNull(result);
        assertEquals("Vårdcentral Norr", result.getName());
    }

    @Test
    void findById() {
        assertTrue(testObject.findById(premises.getId()).isPresent());
    }

    @Test
    void findAll() {
        assertEquals(4, testObject.findAll().size());
    }

    @Test
    void delete() {
        testObject.delete(premises.getId());
        assertNull(em.find(Premises.class, premises.getId()));
    }
}