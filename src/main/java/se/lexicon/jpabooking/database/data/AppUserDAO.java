package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserDAO extends JpaRepository<AppUser, String> {

    Optional<AppUser> findByUsernameIgnoringCase(String username);
    List<AppUser> findByRolesUserRole(UserRole userRole);

}
