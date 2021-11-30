package se.lexicon.jpabooking.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.entity.ContactInfo;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ContactInfoDAOImpl implements ContactInfoDAO{

    private final EntityManager entityManager;

    @Autowired
    public ContactInfoDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ContactInfo> findByEmail(String email) {
        return entityManager.createQuery("SELECT ci FROM ContactInfo ci WHERE UPPER(ci.email) = UPPER(:email)", ContactInfo.class)
                .setParameter("email", email.trim())
                .getResultStream()
                .findFirst();
    }

    @Override
    public ContactInfo save(ContactInfo entity) {
        if(entity == null) throw new IllegalArgumentException("Entity was null");
        if(entity.getId() == null){
            entityManager.persist(entity);
        }else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<ContactInfo> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(ContactInfo.class, id)
        );
    }

    @Override
    public List<ContactInfo> findAll() {
        return entityManager.createQuery("SELECT ci FROM ContactInfo ci", ContactInfo.class)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
