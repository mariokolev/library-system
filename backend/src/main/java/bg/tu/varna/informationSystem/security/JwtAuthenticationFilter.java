package bg.tu.varna.informationSystem.security;

import bg.tu.varna.informationSystem.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    
    private final TokenService tokenService;


    @Autowired
    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

            if (authorizationHeaderIsInvalid(authorizationHeader)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(token);

        } catch (Exception e) {
            logger.error("Cannot authenticate user.");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith(BEARER);
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        String token = authorizationHeader.replace(BEARER, "");
        UserPrincipal userPrincipal = tokenService.parseToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>();

        userPrincipal.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
}
