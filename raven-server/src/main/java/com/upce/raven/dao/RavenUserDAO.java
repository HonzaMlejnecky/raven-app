package com.upce.raven.dao;

import com.upce.raven.dao.bean.*;
import com.upce.raven.entity.RavenUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.support.*;

public interface RavenUserDAO  {

    RavenUser findByUsername(String username);

    Long create(RavenUser user);

}
