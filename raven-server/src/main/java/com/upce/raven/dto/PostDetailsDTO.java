package com.upce.raven.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailsDTO {

    private int numOfLikes;
    private String description;
    private int numberOfComments;
    private Date createdAt;
    private boolean isVideo;
    private String posterUsername;


}
