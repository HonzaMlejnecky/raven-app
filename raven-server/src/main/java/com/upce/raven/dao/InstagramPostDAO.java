package com.upce.raven.dao;

import com.upce.raven.dto.*;
import com.upce.raven.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface InstagramPostDAO  {

    List<InstagramPost> findAllByTrackersUsername(String username);

    Long create(InstagramPost igPost);

    InstagramPost getPostDetails(String igPostId);

    List<InstagramPost> getPostsTimeline(String igPostId);

}
