package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.entity.AppRole;

import java.util.Optional;

public interface AppRoleDAO extends JpaRepository<AppRole, String> {
    @Query("SELECT r FROM AppRole r WHERE r.userRole = :role")
    Optional<AppRole> findByUserRole(@Param("role") UserRole userRole);
}
