package com.upce.raven.service;

import com.upce.raven.dto.*;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.*;

public interface UserService {

    boolean registerUser(UserDTO userDTO);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}
