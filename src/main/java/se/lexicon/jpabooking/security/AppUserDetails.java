package se.lexicon.jpabooking.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class AppUserDetails implements UserDetails, Serializable {

    private String userId;
    private String username;
    private String patientId;
    private String contactId;
    private String email;
    private String password;
    private boolean active;
    private Set<SimpleGrantedAuthority> authorities;

    public AppUserDetails(String userId,
                          String username,
                          String patientId,
                          String contactId,
                          String email,
                          String password,
                          boolean active,
                          Set<SimpleGrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.patientId = patientId;
        this.contactId = contactId;
        this.email = email;
        this.password = password;
        this.active = active;
        this.authorities = authorities;
    }

    public AppUserDetails() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getUserId() {
        return userId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getContactId() {
        return contactId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
