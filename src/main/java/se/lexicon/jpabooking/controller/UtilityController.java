package se.lexicon.jpabooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.jpabooking.model.constants.UserRole;

import java.util.*;

@RestController
public class UtilityController {

    @GetMapping("/api/v1/utilities/roles")
    public ResponseEntity<UserRole[]> getRoles(){
        return ResponseEntity.ok(UserRole.values());
    }

    @GetMapping("/api/v1/utilities/params")
    public ResponseEntity<?> getValidRequestParams(){
        Map<String, List<String>> map = new HashMap<>();
        map.put("users:search", new ArrayList<>(Arrays.asList("role", "username", "all")));
        map.put("bookings:search", new ArrayList<>(Arrays.asList("between", "after", "administrator", "vaccine", "vacant", "city", "all")));
        map.put("patients:search", new ArrayList<>(Arrays.asList("name", "pnr", "all")));
        return ResponseEntity.ok(map);
    }

}
