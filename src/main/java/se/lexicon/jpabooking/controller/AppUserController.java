package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpabooking.model.constants.UserRole;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.dto.view.AppUserDTO;
import se.lexicon.jpabooking.service.facade.AppUserService;

@RestController
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/api/v1/users/admin")
    public ResponseEntity<AppUserDTO> createSuperAdmin(@RequestBody AppUserForm appUserForm){
        return ResponseEntity.status(201).body(appUserService.create(appUserForm, UserRole.ROLE_SUPER_ADMIN));
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<AppUserDTO> createPremisesAdmin(@RequestBody AppUserForm appUserForm){
        return ResponseEntity.status(201).body(appUserService.create(appUserForm, UserRole.ROLE_PREMISES_ADMIN));
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> find(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "value", defaultValue = "") String value
    ){
        switch (search.toLowerCase()){
            case "role":
                return ResponseEntity.ok().body(appUserService.findByUserRole(UserRole.valueOf(value)));
            case "username":
                return ResponseEntity.ok().body(appUserService.findByUsername(value));
            case "all":
                return ResponseEntity.ok().body(appUserService.findAll());
            default:
                throw new IllegalArgumentException("Invalid search value: " + search + ". Valid search values are 'all', 'username' and 'role'.");
        }
    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<AppUserDTO> update(@PathVariable("id") String id, AppUserForm appUserForm){
        return ResponseEntity.ok(appUserService.update(id, appUserForm));
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        appUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
