package com.upce.raven.dao;

import com.upce.raven.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class UserAuthDAO {

    @Autowired
    private RavenUserDAO ravenUserDAO;

    //public User findUserByEmail(String email){
    //    // FIXME: hook on database with users and added password encryption
    //    Collection<? extends GrantedAuthority> cl = new ArrayList();
    //    User user = new User("username", "password", cl);
    //    return user;
    //}

    public User findUserByUsername(String username){
        RavenUser ravenUser = ravenUserDAO.findByUsername(username);

        Collection<? extends GrantedAuthority> cl = new ArrayList();
        User user = new User(ravenUser.getUsername(), ravenUser.getPassword(), cl);
        return user;
    }


}
