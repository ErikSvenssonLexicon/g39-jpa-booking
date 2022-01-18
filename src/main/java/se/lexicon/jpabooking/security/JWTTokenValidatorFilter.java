package se.lexicon.jpabooking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static se.lexicon.jpabooking.security.SecurityConstants.*;

public class JWTTokenValidatorFilter extends BasicAuthenticationFilter {
    public JWTTokenValidatorFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("Authorization");
        if(jwt != null){
            try{
                if(!jwt.startsWith("Bearer ")){
                    throw new BadCredentialsException("Invalid token");
                }

                jwt = jwt.substring("Bearer ".length());
                SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject();
                String authorities = claims.get(AUTHORITIES, String.class);
                Set<SimpleGrantedAuthority> authoritySet = Arrays.stream(authorities.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                String userId = claims.get(USER_ID, String.class);
                String patientId = claims.get(PATIENT_ID, String.class);
                String email = claims.get(EMAIL, String.class);

                AppUserDetails appUserDetails = new AppUserDetails(
                        userId, username, patientId, null, email, null, true, authoritySet
                );
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        appUserDetails, null, authoritySet
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("Invalid token received");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/public/auth");
    }
}
