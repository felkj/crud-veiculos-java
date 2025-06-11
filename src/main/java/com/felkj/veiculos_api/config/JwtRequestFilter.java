package com.felkj.veiculos_api.config;

import com.felkj.veiculos_api.dto.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService; // Seu UserService

    @Autowired
    private JwtUtil jwtUtil;
/*
* Lógica principal dos tokens JWT
* */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtém o cabeçalho Authorization
        final String authorizationHeader = request.getHeader("Authorization");

        System.out.println("DEBUG: Authorization Header recebido: " + authorizationHeader); // LINHA DE DEBUG

        String username = null;
        String jwt = null;

        // Verifica se o cabeçalho Authorization existe e começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7).trim(); // Extrai o token JWT (remove "Bearer ") e remove espaços em branco
            System.out.println("DEBUG: JWT extraído: '" + jwt + "'"); // LINHA DE DEBUG

            try {
                username = jwtUtil.extractUsername(jwt); // Linha 39 na sua stack trace (agora linha 40)
                System.out.println("DEBUG: Username extraído do JWT: " + username); // LINHA DE DEBUG
            } catch (io.jsonwebtoken.MalformedJwtException e) {
                // Melhoria: Mostra qual string causou o erro de token malformado
                System.err.println("ERRO JWT: Token malformado detectado! String recebida: '" + jwt + "' Erro: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                return; // Interrompe a cadeia de filtros
            } catch (Exception e) {
                System.err.println("ERRO JWT: Outro erro ao processar token '" + jwt + "': " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                return;
            }
        } else {
            System.out.println("DEBUG: Authorization Header ausente ou não começa com Bearer."); // LINHA DE DEBUG
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("DEBUG: Usuário autenticado no contexto de segurança."); // LINHA DE DEBUG
            } else {
                System.out.println("DEBUG: Token inválido ou expirado."); // LINHA DE DEBUG
            }
        }
        chain.doFilter(request, response);
    }
}
