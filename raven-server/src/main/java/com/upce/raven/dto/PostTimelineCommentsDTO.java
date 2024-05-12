package com.upce.raven.dto;

import lombok.*;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostTimelineCommentsDTO {

    private Date datetime;
    private Long numOfComments;

}
