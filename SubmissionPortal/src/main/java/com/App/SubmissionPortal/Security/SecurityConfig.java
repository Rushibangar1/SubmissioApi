package com.App.SubmissionPortal.Security;

import com.App.SubmissionPortal.Filter.JwtAuthenticationFilter;
import com.App.SubmissionPortal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {


    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;





    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Disable CSRF (needed for POST requests with JWT)
                .authorizeRequests()
                // Permit all for the register and login endpoints for both users and admins
                .requestMatchers("/api/users/register", "/api/admin/register", "/api/users/login", "/api/admin/login").permitAll()

                // User-related endpoints - only authenticated users with the USER role
                .requestMatchers("/api/users/upload-assignment", "/api/users/assignments").hasRole("USER")

                // Admin-related endpoints - only authenticated users with the ADMIN role
                .requestMatchers("/api/admin/assignments", "/api/admin/assignments/accept/**", "/api/admin/assignments/reject/**").hasRole("ADMIN")

                // All other requests need authentication (including any other endpoints under /api/users or /api/admin)
                .anyRequest().authenticated()

                .and()
                // Add the custom JWT authentication filter after permitAll()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
