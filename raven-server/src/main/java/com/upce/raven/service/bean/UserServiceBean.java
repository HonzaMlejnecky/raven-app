package com.upce.raven.service.bean;

import com.upce.raven.dao.*;
import com.upce.raven.dto.*;
import com.upce.raven.entity.RavenUser;
import com.upce.raven.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserServiceBean implements UserDetailsService, UserService {

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Autowired
    private RavenUserDAO ravenUserDAO;

    @Override
    public boolean registerUser(UserDTO userDTO) {
        RavenUser user = new RavenUser();

        user.setEmail(userDTO.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPwd = passwordEncoder.encode(userDTO.getPassword());
        System.out.println(encodedPwd);

        user.setPassword(encodedPwd);
        user.setUsername(userDTO.getUsername());
        ravenUserDAO.create(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User user = userAuthDAO.findUserByUsername(username);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }
}
