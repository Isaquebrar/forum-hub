package com.forumhub.dto;

import lombok.Data;

@Data
public class TopicRequestDTO {
    private String title;
    private String message;
    private Long courseId;
}
