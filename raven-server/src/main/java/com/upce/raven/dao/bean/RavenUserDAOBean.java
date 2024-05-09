package com.upce.raven.dao.bean;

import com.upce.raven.dao.*;
import com.upce.raven.entity.*;
import jakarta.persistence.Query;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.lang.annotation.*;
import java.util.*;
import java.util.function.*;

@Repository
public class RavenUserDAOBean extends GenericDAOBean<RavenUser, Long> implements RavenUserDAO {

    public RavenUserDAOBean() {
        super(RavenUser.class);
    }

    @Override
    @Transactional
    public RavenUser findByUsername(String username) {
        String hql = "SELECT rUser FROM RavenUser rUser WHERE rUser.username = :username";

        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        return (RavenUser) query.getSingleResult();
    }


    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Long create(RavenUser user) {
        return (Long) getSession().save(user);
    }

}
