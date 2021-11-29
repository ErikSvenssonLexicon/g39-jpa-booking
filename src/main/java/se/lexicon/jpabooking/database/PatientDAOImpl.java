package se.lexicon.jpabooking.database;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientDAOImpl implements PatientDAO{

    private final EntityManager entityManager;

    public PatientDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Patient save(Patient patient) {
        if(patient == null) throw new IllegalArgumentException("Patient was null");
        if(patient.getId() == null){
            return persist(patient);
        }
        return entityManager.merge(patient);
    }

    public Patient persist(Patient patient) {
        entityManager.persist(patient);
        return patient;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findById(String id) {
        Patient patient = entityManager.find(Patient.class, id);
        return Optional.ofNullable(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findByPnr(String pnr) {
        return entityManager.createQuery("SELECT p FROM Patient p WHERE p.pnr = :pnr", Patient.class)
                .setParameter("pnr", pnr.replaceAll(" ", "").replaceAll("-", "").trim())
                .getResultStream()
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return entityManager.createQuery("SELECT p FROM Patient p", Patient.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> searchByName(String name) {
        return entityManager.createQuery("SELECT p FROM Patient p WHERE UPPER(CONCAT(p.firstName, ' ', p.lastName)) LIKE UPPER(CONCAT('%', :name, '%'))", Patient.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
