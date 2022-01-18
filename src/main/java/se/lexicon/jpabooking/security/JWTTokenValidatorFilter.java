package se.lexicon.jpabooking.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static se.lexicon.jpabooking.security.SecurityConstants.AUTHORIZATION;
import static se.lexicon.jpabooking.security.SecurityConstants.BEARER;

public class JWTTokenValidatorFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;



    public JWTTokenValidatorFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(AUTHORIZATION);
        if(jwt != null){
            try{
                if(!jwt.startsWith(BEARER)){
                    throw new BadCredentialsException("Invalid token");
                }
                jwt = jwt.substring(BEARER.length());
                Claims claims = jwtUtil.parseClaims(jwt);
                AppUserDetails appUserDetails = jwtUtil.fromClaims(claims);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        appUserDetails, null, appUserDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("Invalid token received");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/public/auth");
    }
}
