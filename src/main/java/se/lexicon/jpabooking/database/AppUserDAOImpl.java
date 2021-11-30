package se.lexicon.jpabooking.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.entity.AppUser;
import se.lexicon.jpabooking.model.constants.UserRole;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AppUserDAOImpl implements AppUserDAO{

    private final EntityManager entityManager;

    @Autowired
    public AppUserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM AppUser u WHERE UPPER(u.username) = UPPER(:username)", AppUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<AppUser> findByUserRole(UserRole role) {
        return entityManager.createQuery("SELECT u FROM AppUser u JOIN FETCH u.roles as role WHERE role = :role", AppUser.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public AppUser save(AppUser entity) {
        if(entity == null) throw new IllegalArgumentException("Entity was null");
        if(entity.getId() == null){
            entityManager.persist(entity);
        }else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<AppUser> findById(String id) {
        return Optional.ofNullable(
                entityManager.find(AppUser.class, id)
        );
    }

    @Override
    public List<AppUser> findAll() {
        return entityManager.createQuery("SELECT u FROM AppUser u", AppUser.class)
                .getResultList();
    }

    @Override
    public void delete(String id) {
        findById(id).ifPresent(entityManager::remove);
    }
}
