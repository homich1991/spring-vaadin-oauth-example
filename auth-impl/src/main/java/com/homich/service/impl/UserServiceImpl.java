package com.homich.service.impl;

import com.homich.entities.Role;
import com.homich.entities.User;
import com.homich.entities.security.SecurityUser;
import com.homich.repositories.UserRepository;
import com.homich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return new SecurityUser(user.getName(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {
        return AuthorityUtils.createAuthorityList(roles.stream().map(Enum::name).toArray(String[]::new));
    }
}
