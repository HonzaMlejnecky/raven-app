package com.upce.raven.dao.bean;

import com.upce.raven.dao.*;
import com.upce.raven.dto.*;
import com.upce.raven.entity.*;
import jakarta.persistence.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Repository
public class InstgramPostDAOBean extends GenericDAOBean<InstagramPost, Long> implements InstagramPostDAO {


    public InstgramPostDAOBean() {
        super(InstagramPost.class);
    }

    @Override
    public List<InstagramPost> findAllByTrackersUsername(String username) {
        String hql = "SELECT ig FROM InstagramPost ig " +
                " INNER JOIN RavenUser user" +
                " WHERE user.username = :username" +
                " ORDER BY ig.createdAt";

        Query query = getSession().createQuery(hql);

        return (List<InstagramPost>) query.getResultList();
    }


    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public Long create(InstagramPost igPost) {
        return (Long) getSession().save(igPost);
    }

    @Override
    @Transactional
    public InstagramPost getPostDetails(String igPostId) {
        String hql = "SELECT ig FROM InstagramPost ig WHERE ig.shortcode = :igPostShortcode ORDER BY ig.createdAt DESC";

        Query query = getSession().createQuery(hql);
        query.setParameter("igPostShortcode", igPostId);
        query.setMaxResults(1);

        return (InstagramPost) query.getSingleResult();
    }

    @Override
    @Transactional
    public List<InstagramPost> getPostsTimeline(String igPostId) {
        String hql = "SELECT ig FROM InstagramPost ig WHERE ig.shortcode = :igPostShortcode";

        Query query = getSession().createQuery(hql);
        query.setParameter("igPostShortcode", igPostId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<String> getUserTrackedPosts(String username) {
        String hql = "SELECT DISTINCT ig.shortcode FROM InstagramPost ig" +
                " INNER JOIN ig.ravenUser user " +
                " WHERE user.username = :username";
        Query query = getSession().createQuery(hql);
        query.setParameter("username", username);

        return query.getResultList();
    }
}
