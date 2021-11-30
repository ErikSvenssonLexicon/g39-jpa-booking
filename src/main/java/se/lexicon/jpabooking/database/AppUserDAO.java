package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.entity.AppUser;
import se.lexicon.jpabooking.model.constants.UserRole;

import java.util.List;
import java.util.Optional;

public interface AppUserDAO extends DAOGenericCRUD<AppUser, String>{
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByUserRole(UserRole role);
}
