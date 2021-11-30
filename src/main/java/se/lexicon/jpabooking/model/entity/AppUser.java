package se.lexicon.jpabooking.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static se.lexicon.jpabooking.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.jpabooking.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    private String id;
    @Column(length = 100, unique = true)
    private String username;
    private String password;
    @ManyToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY,
            mappedBy = "appUsers"
    )
    private Set<AppRole> roles;

    public AppUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public AppUser() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AppRole> getRoles() {
        if(roles == null) roles = new HashSet<>();
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        if(roles == null) roles = new HashSet<>();
        if(roles.isEmpty()){
            if(this.roles != null){
                this.roles.forEach(appRole -> appRole.getAppUsers().remove(this));
            }
        }else {
            roles.forEach(appRole -> appRole.getAppUsers().add(this));
        }
        this.roles = roles;
    }

    public void addAppRole(AppRole appRole){
        if(appRole == null) throw new IllegalArgumentException("AppRole was null");
        if(roles == null) roles = new HashSet<>();
        roles.add(appRole);
        appRole.getAppUsers().add(this);
    }

    public void removeAppRole(AppRole appRole){
        if(appRole == null) throw new IllegalArgumentException("AppRole was null");
        if(roles == null) roles = new HashSet<>();
        roles.remove(appRole);
        appRole.getAppUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
