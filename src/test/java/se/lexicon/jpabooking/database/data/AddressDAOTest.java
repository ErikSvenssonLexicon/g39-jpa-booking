package se.lexicon.jpabooking.database.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.jpabooking.model.entity.Address;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressDAOTest {

    @Autowired
    private AddressDAO testObject;

    @Autowired
    private TestEntityManager em;

    @Test
    void findByStreetZipCodeAndCity() {
        String streetAddress = "Storgatan 1";
        String zipCode = "35256";
        String city = "Växjö";
        Address address = em.persist(new Address(null, streetAddress, zipCode, city));

        Optional<Address> result = testObject.findByStreetZipCodeAndCity(streetAddress, zipCode, city);
        assertTrue(result.isPresent());
        Address resultAddress = result.get();
        assertEquals(address.getId(), resultAddress.getId());
    }
}