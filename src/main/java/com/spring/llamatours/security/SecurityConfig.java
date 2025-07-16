package com.spring.llamatours.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/usuarios/registro", "/usuarios/guardar", "/css/**", "/js/**", "/registro", "/login", "/imagenes/**","/h2-console/**", "/contacto", "/nosotros").permitAll()
                .requestMatchers("/viaje/**").authenticated()
                .requestMatchers("/perfil").hasRole("USUARIO")
                .requestMatchers("/pagos/**", "/reservaciones/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/usuarios/**","/destinos/**","/pagos/lista").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .headers(headers -> headers.frameOptions().disable()) // habilita frames (necesario para H2 Console)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // permite acceder sin CSRF a consola H2

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Para usar AuthenticationManager en caso necesites autenticar manualmente
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
