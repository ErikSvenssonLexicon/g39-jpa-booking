package se.lexicon.jpabooking.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppRoleDAO;
import se.lexicon.jpabooking.database.AppUserDAO;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.model.entity.AppUser;

import java.util.List;

@Service
@Transactional
public class AppUserEntityServiceImpl implements AppUserEntityService{

    private final AppUserDAO appUserDAO;
    private final AppRoleDAO appRoleDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserEntityServiceImpl(AppUserDAO appUserDAO, AppRoleDAO appRoleDAO, PasswordEncoder passwordEncoder) {
        this.appUserDAO = appUserDAO;
        this.appRoleDAO = appRoleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserDAO.findByUsername(username)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find user with provided username"));
    }

    @Override
    public List<AppUser> findByUserRole(UserRole role) {
        return appUserDAO.findByUserRole(role);
    }

    @Override
    public AppUser create(AppUserForm appUserForm) {
        if(appUserForm == null){
            throw new IllegalArgumentException("AppUserForm was null");
        }
        return internalCreate(appUserForm, UserRole.ROLE_PATIENT_USER);
    }

    public AppUser internalCreate(AppUserForm appUserForm, UserRole userRole){
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserForm.getUsername());
        appUser.setPassword(
                passwordEncoder.encode(appUserForm.getPassword())
        );
        AppRole role = appRoleDAO.findByUserRole(userRole)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find AppRole"));
        appUser.addAppRole(role);

        return appUserDAO.save(appUser);
    }

    @Override
    public AppUser createAdminUser(AppUserForm appUserForm){
        if(appUserForm == null){
            throw new IllegalArgumentException("AppUserForm was null");
        }
        return internalCreate(appUserForm, UserRole.ROLE_SUPER_ADMIN);
    }

    @Override
    public AppUser createPremisesAdmin(AppUserForm appUserForm){
        if(appUserForm == null){
            throw new IllegalArgumentException("AppUserForm was null");
        }
        return internalCreate(appUserForm, UserRole.ROLE_PREMISES_ADMIN);
    }

    @Override
    public AppUser findById(String id) {
        return appUserDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find user with provided id " + id));
    }

    @Override
    public List<AppUser> findAll() {
        return appUserDAO.findAll();
    }

    @Override
    public AppUser update(String id, AppUserForm appUserForm) {
        AppUser appUser = findById(id);
        appUser.setUsername(appUserForm.getUsername());
        appUser.setPassword(appUserForm.getPassword());
        appUser = appUserDAO.save(appUser);
        return appUser;
    }

    @Override
    public void delete(String id) {
        appUserDAO.delete(id);
    }
}
