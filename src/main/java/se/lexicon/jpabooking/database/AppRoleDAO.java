package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.AppRole;
import se.lexicon.jpabooking.model.constants.UserRole;

import java.util.Optional;

public interface AppRoleDAO extends DAOGenericCRUD<AppRole, String> {
    Optional<AppRole> findByUserRole(UserRole userRole);
}
