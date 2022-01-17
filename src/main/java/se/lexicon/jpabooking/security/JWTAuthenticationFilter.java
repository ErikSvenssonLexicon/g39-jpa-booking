package se.lexicon.jpabooking.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static se.lexicon.jpabooking.security.SecurityConstants.JWT_KEY;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/public/auth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginCommand loginCommand = null;
        try{
            loginCommand = new ObjectMapper().readValue(request.getInputStream(), LoginCommand.class);
        }catch (IOException ex){
            throw new AuthenticationCredentialsNotFoundException("Missing credentials");
        }

        if(loginCommand != null && loginCommand.getUsername() != null && loginCommand.getPassword() != null){
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCommand.getUsername(), loginCommand.getPassword()
                    )
            );
        }else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        AppUserDetails appUserDetails = (AppUserDetails) authResult.getPrincipal();

        if(appUserDetails != null){
            SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setIssuer("jpa-booking")
                    .setHeaderParam("typ", "JWT")
                    .setSubject(appUserDetails.getUsername())
                    .claim("authorities", populateAuthorities(appUserDetails.getAuthorities()))
                    .claim("userId", appUserDetails.getUserId())
                    .claim("patientId", appUserDetails.getPatientId())
                    .claim("email", appUserDetails.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(
                            System.currentTimeMillis() + 3_600_000
                    ))
                    .signWith(key, SignatureAlgorithm.HS512).compact();

            Map<String, String> body = new HashMap<>();
            body.put("accessToken", jwt);
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            response.getWriter().write(json);
        }

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority : authorities){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
