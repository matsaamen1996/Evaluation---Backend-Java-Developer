package com.is.evaluation.security.service.impl;

import com.is.evaluation.exception.Message;
import com.is.evaluation.exception.MessageDescription;
import com.is.evaluation.model.entity.Role;
import com.is.evaluation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.is.evaluation.model.entity.User user = userService.getUserByUserName(username);

        if (user == null) {
            Object[] obj = {username};
            throw new UsernameNotFoundException(Message.GetNotFound(MessageDescription.UsernameNotFound, obj).getMessage());
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        if (authorities.isEmpty()) {
            Object[] obj = {username};
            throw new UsernameNotFoundException(Message.GetNotFound(MessageDescription.UserWithoutRoles, obj).getMessage());
        }

        boolean enabled = !user.getState().equals("BLOQUEADO");

        return new User(user.getUsername(), user.getPassword(), enabled, true, true, true, authorities);
    }
}
