package com.upce.raven.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class PostTimelineDTO {

    private Date datetime;
    private Long numOfLikes;

}
