package se.lexicon.jpabooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.AppUserDAO;
import se.lexicon.jpabooking.model.entity.AppRole;
import se.lexicon.jpabooking.model.entity.AppUser;
import se.lexicon.jpabooking.model.entity.Booking;
import se.lexicon.jpabooking.model.entity.Patient;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    @Autowired
    public AppUserDetailsServiceImpl(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with username " + username + " was found"));

        AppUserDetails appUserDetails = new AppUserDetails();
        appUserDetails.setUserId(appUser.getId());
        appUserDetails.setUsername(username);
        appUserDetails.setPassword(appUser.getPassword());
        appUserDetails.setActive(true);
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        for(AppRole appRole : appUser.getRoles()){
            authoritySet.add(new SimpleGrantedAuthority(appRole.getUserRole().name()));
        }
        if(appUser.getPatient() != null){
            Patient patient = appUser.getPatient();
            appUserDetails.setPatientId(patient.getId());
            appUserDetails.setContactId(patient.getContactInfo().getId());
            appUserDetails.setEmail(patient.getContactInfo().getEmail());
            for(Booking booking : patient.getVaccineBookings()){
                authoritySet.add(new SimpleGrantedAuthority(booking.getId()));
            }
        }
        appUserDetails.setAuthorities(authoritySet);
        return appUserDetails;
    }



}
