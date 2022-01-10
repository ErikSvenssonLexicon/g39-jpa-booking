package se.lexicon.jpabooking.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppRoleDAO;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.entity.AppRole;

import javax.annotation.PostConstruct;

@Component
public class DatabaseMaintenance {

    private final AppRoleDAO appRoleDAO;

    @Autowired
    public DatabaseMaintenance(AppRoleDAO appRoleDAO) {
        this.appRoleDAO = appRoleDAO;
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
