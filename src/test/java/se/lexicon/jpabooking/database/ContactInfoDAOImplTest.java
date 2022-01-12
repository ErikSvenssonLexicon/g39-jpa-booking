package se.lexicon.jpabooking.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.jpabooking.model.entity.ContactInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactInfoDAOImplTest {

    public static final String EMAIL = "nisse@gmail.com";
    public static final String PHONE = "0701234567";
    @Autowired
    private ContactInfoDAO testObject;
    @Autowired
    private TestEntityManager em;

    public List<ContactInfo> contactInfos(){
        return Arrays.asList(
                new ContactInfo(null, EMAIL, PHONE),
                new ContactInfo(null, "olle@gmail.com", "0704536325"),
                new ContactInfo(null, "anna@gmail.com", "0702853578")
        );
    }

    private ContactInfo contactInfo;

    @BeforeEach
    void setUp() {
        List<ContactInfo> contactInfos = contactInfos().stream()
                .map(em::persist)
                .collect(Collectors.toList());
        contactInfo = contactInfos.get(0);
    }

    @Test
    void findByEmail() {
        String email = EMAIL.toUpperCase();
        assertTrue(testObject.findByEmail(email).isPresent());
    }

    @Test
    void save_persist() {
        ContactInfo contactInfo = new ContactInfo(null, "arne.anka@hotmail.com", null);
        ContactInfo result = testObject.save(contactInfo);

        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void save_update() {
        ContactInfo contactInfo = this.contactInfo;
        contactInfo.setEmail("herr.nilsson@gmail.com");

        ContactInfo result = testObject.save(contactInfo);

        assertNotNull(result);
        assertEquals(contactInfo.getId(), result.getId());
        assertEquals("herr.nilsson@gmail.com", result.getEmail());
    }

    @Test
    void findById() {
        Optional<ContactInfo> result = testObject.findById(contactInfo.getId());
        assertTrue(result.isPresent());
        ContactInfo contactInfo = result.get();
        assertEquals(this.contactInfo, contactInfo);
    }

    @Test
    void findAll() {
        int expectedSize = 3;
        assertEquals(expectedSize, testObject.findAll().size());
    }

    @Test
    void delete() {
        testObject.deleteById(contactInfo.getId());
        assertNull(em.find(ContactInfo.class, contactInfo.getId()));
    }
}