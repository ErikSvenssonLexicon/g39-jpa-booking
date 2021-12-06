package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.lexicon.jpabooking.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends JpaRepository<Patient, String> {
    Optional<Patient> findByPnr(String pnr);
    @Query("SELECT p FROM Patient p WHERE UPPER(CONCAT(p.firstName, ' ', p.lastName)) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Patient> findByName(String name);
}
