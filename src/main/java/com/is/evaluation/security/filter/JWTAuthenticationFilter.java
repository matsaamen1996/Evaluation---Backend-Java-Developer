package com.is.evaluation.security.filter;

import com.is.evaluation.model.entity.User;
import com.is.evaluation.security.service.JWTService;
import com.is.evaluation.security.service.impl.JWTServiceImpl;
import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.role.RoleQueryDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JWTService jwtService,
                                   UserService userService) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = "";
        String password = "";

        User user = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            username = user.getUsername();
            password = user.getPassword();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = authResult.getPrincipal() instanceof UserDetails ? ((UserDetails) authResult.getPrincipal()).getUsername() : "";

        User user = userService.getUserByUserName(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<RoleQueryDto> roleQueryDtoList = userService.getUserRolesByUserId(user.getId());
        String[] roles = roleQueryDtoList.stream().filter(RoleQueryDto::isAssigned).map(RoleQueryDto::getRole).toArray(String[]::new);
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        String token = jwtService.create(authResult, authorities);

        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", authResult.getPrincipal());
        body.put("name", (user.getName()));
        body.put("phone", (user.getTelephone()));
        body.put("email", (user.getEmail()));
        body.put("mensaje", String.format("Hello %s", username));
        body.put("completed", user.getCompleted());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json;charset=ISO-8859-1");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Authentication error");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json;charset=ISO-8859-1");
    }
}
