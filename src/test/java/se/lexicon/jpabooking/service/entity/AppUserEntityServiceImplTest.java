package se.lexicon.jpabooking.service.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.model.entity.AppUser;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AppUserEntityServiceImplTest {

    @Autowired
    private AppUserEntityServiceImpl testObject;
    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        Stream.of(UserRole.values())
                .forEach(userRole -> em.persist(new AppRole(userRole)));
    }

    @Test
    void create() {
        AppUserForm form = new AppUserForm();
        form.setUsername("Karmand.Aziz");
        form.setPassword("Karmand");

        AppUser result = testObject.create(form);
        em.flush();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Karmand", result.getPassword());
        assertEquals("Karmand.Aziz", result.getUsername());
        assertTrue(result.getRoles().stream().anyMatch(appRole -> appRole.getUserRole() == UserRole.ROLE_PATIENT_USER));
    }

    @Test
    void update() {
    }
}