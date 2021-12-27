package se.lexicon.jpabooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppUserDAO;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.model.entity.AppUser;

import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    @Autowired
    public UserDetailsServiceImpl(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " could not be found"));

        Collection<GrantedAuthority> authorities = new HashSet<>();
        for(AppRole role : appUser.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getUserRole().name()));
        }

        return new BookingUserDetails(
                appUser.getPatient().getId(),
                appUser.getId(),
                appUser.getUsername(),
                appUser.getPassword(),
                authorities
        );
    }
}
