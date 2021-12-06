package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.jpabooking.model.entity.ContactInfo;

import java.util.Optional;

public interface ContactInfoDAO extends JpaRepository<ContactInfo, String> {
    Optional<ContactInfo> findByEmailIgnoringCase(String email);
}
