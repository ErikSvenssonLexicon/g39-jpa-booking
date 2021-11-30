package se.lexicon.jpabooking.database;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.entity.Premises;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PremisesDAOImpl implements PremisesDAO{

    private final EntityManager entityManager;

    public PremisesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Premises save(Premises entity) {
        if(entity == null) throw new IllegalArgumentException("Entity was null");
        if(entity.getId() == null){
            entityManager.persist(entity);
        }else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<Premises> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(Premises.class, id)
        );
    }

    @Override
    public List<Premises> findAll() {
        return entityManager.createQuery("SELECT p FROM Premises p", Premises.class)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
