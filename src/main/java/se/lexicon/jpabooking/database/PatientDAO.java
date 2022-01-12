package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends JpaRepository<Patient, String> {
    @Query("SELECT p FROM Patient p WHERE UPPER(p.userCredentials.username) = UPPER(:username)")
    Optional<Patient> findByUsername(@Param("username") String username);

    @Query("SELECT p FROM Patient p WHERE p.pnr = :pnr")
    Optional<Patient> findByPnr(@Param("pnr") String pnr);

    @Query("SELECT p FROM Patient p WHERE UPPER(CONCAT(p.firstName, ' ', p.lastName)) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Patient> searchByName(@Param("name") String name);

}
