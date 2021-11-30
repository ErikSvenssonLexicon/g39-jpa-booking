package se.lexicon.jpabooking.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.entity.Address;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AddressDAOImpl implements AddressDAO{

    private final EntityManager entityManager;

    @Autowired
    public AddressDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Address> findByStreetZipCodeAndCity(String street, String zip, String city) {
        return entityManager.createQuery("SELECT a FROM Address a WHERE UPPER(a.streetAddress) = UPPER(:street) AND a.zipCode = :zip AND UPPER(a.city) = UPPER(:city)", Address.class)
                .setParameter("street", street)
                .setParameter("zip", zip)
                .setParameter("city", city)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Address save(Address entity) {
        if(entity == null) throw new IllegalArgumentException("Entity was null");
        if(entity.getId() == null){
            entityManager.persist(entity);
        }else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<Address> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(Address.class, id)
        );
    }

    @Override
    public List<Address> findAll() {
        return entityManager.createQuery("SELECT a FROM Address a", Address.class)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
