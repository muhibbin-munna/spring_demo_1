package com.example.project1.security;

import com.example.project1.entity.User;
import com.example.project1.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.project1.config.WebSecurityConfig.WHITE_LIST_URLS;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Filter accessed");
        //Authorization
        String requestHeader = request.getHeader("Authorization");
        logger.info(" Header :  {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null) {
            try {
                username = this.jwtHelper.getUsernameFromToken(requestHeader);
            } catch (IllegalArgumentException e) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Illegal Argument while fetching the username !!");
            } catch (ExpiredJwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Given jwt token is expired !!");
            } catch (MalformedJwtException e) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid Token!!");
            } catch (Exception e) {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //fetch user detail from username
            User user = this.userService.findUserByEmail(username);

            Boolean validateToken = this.jwtHelper.validateToken(token, user);
            if (validateToken) {
                //set the authentication
                final Claims claims = jwtHelper.getAllClaimsFromToken(token);

                final Collection<? extends GrantedAuthority> authorities =
                        Arrays.stream(claims.get("role").toString().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization header not found");
            }


        }
        filterChain.doFilter(request, response);

    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(message);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
