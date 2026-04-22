package com.apmst.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(
	            AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf
	            		.ignoringRequestMatchers("/tasks/api/**", "/send-request")
	            )
	            .userDetailsService(userDetailsService)
	            .authorizeHttpRequests(auth -> auth
	                // ADD "/" HERE to make the landing page public
	            		.requestMatchers("/", "/login", "/send-request", "/register", "/css/**", "/js/**", "/images/**").permitAll()
	                
	                // Admin only
	                .requestMatchers("/admin/**").hasRole("ADMIN")
	                // Manager only
	                .requestMatchers("/manager/**").hasRole("PROJECT_MANAGER")
	                // All authenticated users
	                .anyRequest().authenticated()
	            )
	            
	         // ADD THIS PART:
	            .exceptionHandling(exception -> exception
	                .accessDeniedPage("/access-denied")
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .defaultSuccessUrl("/dashboard", true)
	                .failureUrl("/login?error=true")
	                .permitAll()
	            )
	            .logout(logout -> logout
	            	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
	            	    .logoutSuccessUrl("/") // Redirect to your index page
	            	    .invalidateHttpSession(true) // Destroy session
	            	    .deleteCookies("JSESSIONID") // Clear cookies
	            	    .permitAll()
	            	);

	        return http.build();
	    }
}