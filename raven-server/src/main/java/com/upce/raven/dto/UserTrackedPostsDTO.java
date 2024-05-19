package com.upce.raven.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class UserTrackedPostsDTO {

    private String username;
    private List<PostShortDTO> postShortDTOs;

}
