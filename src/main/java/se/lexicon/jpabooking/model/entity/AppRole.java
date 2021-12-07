package se.lexicon.jpabooking.model.entity;

import org.hibernate.annotations.GenericGenerator;
import se.lexicon.jpabooking.model.constants.UserRole;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static se.lexicon.jpabooking.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.jpabooking.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class AppRole {

    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    private String id;
    private UserRole userRole;
    @ManyToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "role_app_user",
            joinColumns = @JoinColumn(name = "fk_app_role_id", table = "role_app_user"),
            inverseJoinColumns = @JoinColumn(name = "fk_app_user_id", table = "role_app_user")
    )
    private Set<AppUser> appUsers;

    public AppRole(String id, UserRole userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    public AppRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public AppRole() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<AppUser> getAppUsers() {
        if(appUsers == null) appUsers = new HashSet<>();
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        if(appUsers == null) appUsers = new HashSet<>();
        if(appUsers.isEmpty()){
            if(this.appUsers != null){
                this.appUsers.forEach(appUser -> appUser.getRoles().remove(this));
            }
        }else {
            appUsers.forEach(appUser -> appUser.getRoles().add(this));
        }
        this.appUsers = appUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppRole appRole = (AppRole) o;
        return userRole == appRole.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRole);
    }

    @Override
    public String toString() {
        return "AppRole{" +
                "id='" + id + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
