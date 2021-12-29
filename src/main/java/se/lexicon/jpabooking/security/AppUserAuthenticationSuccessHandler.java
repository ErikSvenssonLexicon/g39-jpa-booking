package se.lexicon.jpabooking.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AppUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        BookingUserDetails bookingUserDetails = (BookingUserDetails) authentication.getPrincipal();

        if(bookingUserDetails.getPatientId() != null){
            response.sendRedirect("/patients/"+bookingUserDetails.getPatientId());
        }else{
            response.sendRedirect("/index");
        }
    }
}
