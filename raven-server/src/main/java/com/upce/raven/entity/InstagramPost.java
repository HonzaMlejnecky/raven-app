package com.upce.raven.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import java.io.*;
import java.util.*;

@Entity
@Table(name = "IG01_POST")
@NoArgsConstructor
@Getter
@Setter
public class InstagramPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ig_post_sequence")
    @SequenceGenerator(name = "ig_post_sequence", sequenceName = "ig01_instagram_post_id_seq", allocationSize = 1)
    @Column(name = "id_ig01")
    private Long idIG01;

    @Column(name = "poster_username")
    private String posterUsername;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_likes")
    private Long numOfLikes;

    @Column(name = "number_of_comments")
    private Long numOfComments;

    @Column(name = "is_video")
    private boolean isVideo;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "shortcode")
    private String shortcode;

    @ManyToOne
    @JoinColumn(name = "us01_id")
    private RavenUser ravenUser;


}
