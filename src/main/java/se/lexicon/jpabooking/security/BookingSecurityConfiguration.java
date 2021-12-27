package se.lexicon.jpabooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BookingSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserAuthenticationSuccessHandler appUserAuthenticationSuccessHandler;

    @Autowired
    public BookingSecurityConfiguration(AppUserAuthenticationSuccessHandler appUserAuthenticationSuccessHandler) {
        this.appUserAuthenticationSuccessHandler = appUserAuthenticationSuccessHandler;
    }

    /*
        ImMemory authentication - Need to hard code users- not viable in a dynamic system with many users
        JDBC authentication - Need to follow strict table structure. Can't use this strategy
        DAO authentication - Need to follow strict table structure. Creates a dao
        Custom authentication - Implement UserDetailsService in a bean. Very viable strategy for our use case
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/index", "/css/**", "/img/**", "/errors/**", "/webjars/**").permitAll()
                    .antMatchers("/public/register", "/public/register/process").anonymous()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/public/login")
                    .successHandler(appUserAuthenticationSuccessHandler)
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/public/logout")
                    .logoutSuccessUrl("/index?logout=true")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/errors/access-denied");
    }
}
