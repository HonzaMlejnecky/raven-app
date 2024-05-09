package com.upce.raven.controller;

import com.upce.raven.constant.*;
import com.upce.raven.dto.*;
import com.upce.raven.entity.*;
import com.upce.raven.service.*;
import com.upce.raven.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(ControllerConstants.USERS_URL)
public class UserController {

    final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(ControllerConstants.USERS_REGISTER_URL)
    public void registerUser(@RequestBody UserDTO userDTO) {

        LOG.info("Registering new user!");
        userService.registerUser(userDTO);
        LOG.info("User registered!");
    }

    @ResponseBody
    @RequestMapping(ControllerConstants.USERS_LOGIN_URL)
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            String username = authentication.getName();
            RavenUser user = new RavenUser();
            user.setUsername(loginDTO.getUsername());
            user.setPassword(loginDTO.getPassword());

            String token = jwtUtil.createToken(user);
            AuthDTO authDTO = new AuthDTO();
            authDTO.setUsername(username);
            authDTO.setToken(token);

            return ResponseEntity.ok(authDTO);

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credentials not found");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
