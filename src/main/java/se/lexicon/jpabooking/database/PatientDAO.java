package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends DAOGenericCRUD<Patient, String>{
    Optional<Patient> findByPnr(String pnr);
    List<Patient> searchByName(String name);
}
