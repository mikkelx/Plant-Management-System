package com.example.pms.security;

//import com.example.aipms.service.DBUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] staticResources  =  {
                "/css/**",
                "/scripts/**",
                "/assets/**"
        };

        http.authorizeRequests(authorization -> authorization
                        .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/personalizedplant/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/user/**").hasAnyAuthority("ADMIN")

//                        .requestMatchers("/personalizedplant/all").hasAuthority("ADMIN")
//                        .requestMatchers("/*").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/bootstrap/**",
                                "/css/**",
                                "/img/**",
                                "/js/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login")
                        .permitAll()

                )
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new DBAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

}
