package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.jpabooking.model.entity.AppRole;

import java.util.Optional;

public interface AppRoleDAO extends JpaRepository<AppRole, String> {
    Optional<AppRole> findByUserRole(AppRole appRole);
}
