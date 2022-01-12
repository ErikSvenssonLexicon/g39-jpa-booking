package se.lexicon.jpabooking.service.facade;

import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.dto.view.AppUserDTO;

import java.util.List;

public interface AppUserService {
    AppUserDTO create(AppUserForm form, UserRole role);
    AppUserDTO findById(String id);
    AppUserDTO findByUsername(String username);
    AppUserDTO addRole(String id, UserRole role);
    AppUserDTO removeRole(String id, UserRole role);
    List<AppUserDTO> findAll();
    List<AppUserDTO> findByUserRole(UserRole role);
    AppUserDTO update(String id, AppUserForm form);
    void delete(String id);
}
