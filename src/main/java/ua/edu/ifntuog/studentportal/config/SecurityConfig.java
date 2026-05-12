package ua.edu.ifntuog.studentportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ifntuog.studentportal.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/error").permitAll()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/grades").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/grades/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/grades/**").hasAnyRole("ADMIN", "PROFESSOR")

                        .requestMatchers(HttpMethod.GET, "/api/grades/**").hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

                        .requestMatchers("/api/faculties/**").hasRole("ADMIN")
                        .requestMatchers("/api/departments/**").hasRole("ADMIN")
                        .requestMatchers("/api/groups/**").hasRole("ADMIN")
                        .requestMatchers("/api/subjects/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                        .requestMatchers("/api/courses/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/professors/**").hasAnyRole("ADMIN", "PROFESSOR", "STUDENT")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
