package se.lexicon.jpabooking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.dto.view.AppUserDTO;
import se.lexicon.jpabooking.service.entity.AppUserEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    private final AppUserEntityService appUserEntityService;
    private final DTOService dtoService;

    @Autowired
    public AppUserServiceImpl(AppUserEntityService appUserEntityService, DTOService dtoService) {
        this.appUserEntityService = appUserEntityService;
        this.dtoService = dtoService;
    }

    @Override
    public AppUserDTO create(AppUserForm form, UserRole role) {
        return dtoService.toFullAppUserDTO(appUserEntityService.create(form, role));
    }

    @Override
    public AppUserDTO findById(String id) {
        return dtoService.toFullAppUserDTO(appUserEntityService.findById(id));
    }

    @Override
    public AppUserDTO findByUsername(String username) {
        return dtoService.toFullAppUserDTO(appUserEntityService.findByUsername(username));
    }

    @Override
    public List<AppUserDTO> findAll() {
        return appUserEntityService.findAll().stream()
                .map(dtoService::toSmallAppUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppUserDTO> findByUserRole(UserRole role) {
        return appUserEntityService.findByUserRole(role).stream()
                .map(dtoService::toSmallAppUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDTO update(String id, AppUserForm form) {
        return dtoService.toFullAppUserDTO(appUserEntityService.update(id, form));
    }

    @Override
    public void delete(String id) {
        appUserEntityService.delete(id);
    }
}
