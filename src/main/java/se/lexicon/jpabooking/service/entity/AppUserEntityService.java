package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.entity.AppUser;

import java.util.List;

public interface AppUserEntityService extends GenericEntityService<AppUser, AppUserForm> {
    AppUser findByUsername(String username);
    List<AppUser> findByUserRole(UserRole role);

}
