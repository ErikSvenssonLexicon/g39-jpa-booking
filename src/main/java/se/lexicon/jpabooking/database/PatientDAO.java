package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO {
    Patient save(Patient patient);
    Optional<Patient> findById(String id);
    Optional<Patient> findByPnr(String pnr);
    List<Patient> findAll();
    List<Patient> searchByName(String name);
    void delete(String id);
}
