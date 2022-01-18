package se.lexicon.jpabooking.setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppRoleDAO;
import se.lexicon.jpabooking.database.AppUserDAO;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.service.entity.AppUserEntityService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;

@Component
public class DatabaseMaintenance {

    private final AppRoleDAO appRoleDAO;
    private final AppUserDAO appUserDAO;
    private final AppUserEntityService appUserEntityService;
    private final Logger logger = LoggerFactory.getLogger(DatabaseMaintenance.class);
    @Value("${spring.profiles.active}")
    private String profile;

    public DatabaseMaintenance(AppRoleDAO appRoleDAO, AppUserDAO appUserDAO, AppUserEntityService appUserEntityService) {
        this.appRoleDAO = appRoleDAO;
        this.appUserDAO = appUserDAO;
        this.appUserEntityService = appUserEntityService;
    }

    @PostConstruct
    @Transactional
    public void postConstruction(){
        if(!profile.equals("test")){
            if(appRoleDAO.findAll().isEmpty()){
                for(UserRole role : UserRole.values()){
                    appRoleDAO.save(new AppRole(role));
                }
            }
            if(appUserDAO.findByUserRole(UserRole.ROLE_SUPER_ADMIN).isEmpty()){
                ObjectMapper objectMapper = new ObjectMapper();
                try{
                    AppUserForm form = objectMapper.readValue(Paths.get("json/admin.json").toFile(), AppUserForm.class);
                    appUserEntityService.create(form, UserRole.ROLE_SUPER_ADMIN);
                }catch (IOException ex){
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

    }
}
