package se.lexicon.jpabooking.setup;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppRoleDAO;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.service.entity.AppUserEntityService;

import javax.annotation.PostConstruct;

@Component
public class DatabaseMaintenance {

    private final AppRoleDAO appRoleDAO;
    private final AppUserEntityService appUserEntityService;

    public DatabaseMaintenance(AppRoleDAO appRoleDAO, AppUserEntityService appUserEntityService) {
        this.appRoleDAO = appRoleDAO;
        this.appUserEntityService = appUserEntityService;
    }

    @PostConstruct
    @Transactional
    public void postConstruction(){
        if(appRoleDAO.findAll().isEmpty()){
            for(UserRole role : UserRole.values()){
                appRoleDAO.save(new AppRole(role));
            }
        }
    }

}
