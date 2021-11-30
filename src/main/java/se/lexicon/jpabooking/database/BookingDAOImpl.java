package se.lexicon.jpabooking.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.Booking;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BookingDAOImpl implements BookingDAO{

    private final EntityManager entityManager;

    @Autowired
    public BookingDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Booking> findByDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        Timestamp timeStart = Timestamp.valueOf(start);
        Timestamp timeEnd = Timestamp.valueOf(end);
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.dateTime BETWEEN :startTime AND :endTime", Booking.class)
                .setParameter("startTime", timeStart)
                .setParameter("endTime", timeEnd)
                .getResultList();
    }

    @Override
    public List<Booking> findByDateTimeBefore(LocalDateTime end) {
        Timestamp timeEnd = Timestamp.valueOf(end);
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.dateTime <= :timeEnd", Booking.class)
                .setParameter("timeEnd", timeEnd)
                .getResultList();
    }

    @Override
    public List<Booking> findByDateTimeAfter(LocalDateTime start) {
        Timestamp timeStart = Timestamp.valueOf(start);
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.dateTime >= :timeStart", Booking.class)
                .setParameter("timeStart", timeStart)
                .getResultList();
    }

    @Override
    public List<Booking> findByAdministratorId(String administratorId) {
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.administratorId = :administratorId", Booking.class)
                .setParameter("administratorId", administratorId)
                .getResultList();
    }

    @Override
    public List<Booking> findByVaccineType(String vaccineType) {
        return entityManager.createQuery("SELECT b FROM Booking b WHERE UPPER(b.vaccineType) = UPPER(:vaccineType)", Booking.class)
                .setParameter("vaccineType", vaccineType)
                .getResultList();
    }

    @Override
    public List<Booking> findByVacantStatus(boolean vacantStatus) {
        return entityManager.createQuery("SELECT b FROM Booking b WHERE b.vacant = :vacant", Booking.class)
                .setParameter("vacant", vacantStatus)
                .getResultList();
    }

    @Override
    public Booking save(Booking entity) {
        if(entity == null) throw new IllegalArgumentException("Entity was null");
        if(entity.getId() == null){
            entityManager.persist(entity);
        }else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(Booking.class, id)
        );
    }

    @Override
    public List<Booking> findAll() {
        return entityManager.createQuery("SELECT b FROM Booking b", Booking.class)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}