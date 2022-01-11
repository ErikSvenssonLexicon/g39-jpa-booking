package se.lexicon.jpabooking.model.dto.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.lexicon.jpabooking.model.constants.UserRole;

import java.io.Serializable;
import java.util.List;

public class AppUserDTO implements Serializable {
    private String id;
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserRole> roles;

    public AppUserDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
