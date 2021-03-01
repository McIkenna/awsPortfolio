package com.ikenna.portfolios.security;

import com.ikenna.portfolios.jwt.JwtAuthenticationFilter;
import com.ikenna.portfolios.jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.ikenna.portfolios.security.ApplicationUserRole.ADMIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index", "/css/*","/js/*","/api/***").permitAll()
                .antMatchers("/admin/***").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ikennaAdmin =  User.builder()
                .username("ikenna")
                .password(passwordEncoder.encode("oke2#CHUKWU"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
/*
        UserDetails user1 = User.builder()
            .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles(USER.name())
                .build();
*/
        return new InMemoryUserDetailsManager(
                ikennaAdmin
               // user1
        );
    }
}
