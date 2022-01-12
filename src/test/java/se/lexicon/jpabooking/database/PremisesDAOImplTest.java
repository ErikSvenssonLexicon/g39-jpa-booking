package se.lexicon.jpabooking.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.jpabooking.model.entity.Address;
import se.lexicon.jpabooking.model.entity.Premises;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PremisesDAOImplTest {

    public static final String NAME = "Norr";
    @Autowired
    private PremisesDAO testObject;
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
    void countUsagesByAddressId() {
        Address address = em.persistAndFlush(new Address(null, "Storgatan 1", "12345", "Byhåla"));
        String addressId = address.getId();
        premises.setAddress(address);
        premises = em.merge(premises);

        Long result = testObject.countUsagesByAddressId(addressId);

        assertEquals(1, result);
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
        testObject.deleteById(premises.getId());
        assertNull(em.find(Premises.class, premises.getId()));
    }
}