package com.upce.raven.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CombinedPostDetailsDTO {

    private Long numOfLikesTotal;
    private String description;
    private Long numberOfCommentsTotal;
    private Date createdAt;
    private boolean isVideo;
    private String posterUsername;
    private String imageUrl;
    List<PostTimelineDTO> postTimelineDTOS;

}
