package dev.vitorpaulo.distantlove.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.vitorpaulo.distantlove.model.DetailedUser;
import dev.vitorpaulo.distantlove.service.UserService;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    public static final String[] PUBLIC_URLS = {
            "/v1/auth/**"
    };
    private static final String PREFIX = "Bearer ";

    private final UserService userService;
    private final JwtParser jwtParser;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(PUBLIC_URLS).anyMatch(
                value -> new AntPathMatcher().match("/api" + value, request.getServletPath())
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header != null && header.startsWith(PREFIX)) {
                header = header.replace(PREFIX, "");

                if (jwtParser.isSigned(header)) {
                    final var token = jwtParser.parseClaimsJws(header);

                    if (token.getBody().containsKey("id")) {
                        final var user = new DetailedUser(userService.getUserById(token.getBody().get("id", Long.class)));

                        final var authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority("USER"))
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception exception) {
            log.error("Não foi possível autenticar o usuário: {}", exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
