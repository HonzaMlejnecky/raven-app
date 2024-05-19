package com.upce.raven.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostShortDTO {

    private String posterUsername;
    private String description;
    private Long numberOfLikes;

    private String shortcode;

}
