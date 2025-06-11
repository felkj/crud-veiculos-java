package com.felkj.veiculos_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Alterado de filterChain para securityFilterChain (convenção)
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }));
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs RESTful sem estado
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite requisições OPTIONS (pre-flight CORS)

                        // Endpoint de Autenticação: Deve ser acessível por qualquer um para login.
                        .requestMatchers("/auth/**").permitAll()

                        // Endpoints de Veículos: Regras de autorização baseadas em roles
                        // GET para listagem geral ou por ID: Acessível para ADMIN e USER
                        .requestMatchers(HttpMethod.GET, "/veiculos").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/veiculos/**").hasAnyRole("ADMIN", "USER")

                        // POST para criação: Apenas para ADMIN
                        .requestMatchers(HttpMethod.POST, "/veiculos").hasRole("ADMIN")

                        // PUT para atualização: Apenas para ADMIN
                        .requestMatchers(HttpMethod.PUT, "/veiculos/**").hasRole("ADMIN")

                        // DELETE para exclusão: Apenas para ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/veiculos/**").hasRole("ADMIN")

                        // Qualquer outra requisição não mapeada acima exige autenticação
                        .anyRequest().authenticated()
                // Lida com 403 Forbidden
                )
                // Configura a gestão de sessão como stateless (sem estado) para JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adiciona o filtro JWT customizado antes do filtro de autenticação de usuário/senha padrão
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                // Configura cabeçalhos (ex: para permitir o H2 console em iframe)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
