package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.ContactInfo;

import java.util.Optional;

public interface ContactInfoDAO extends JpaRepository<ContactInfo, String> {
    @Query("SELECT ci FROM ContactInfo ci WHERE UPPER(ci.email) = UPPER(:email)")
    Optional<ContactInfo> findByEmail(@Param("email") String email);
}
