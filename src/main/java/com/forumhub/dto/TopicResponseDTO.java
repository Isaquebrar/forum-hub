package com.forumhub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TopicResponseDTO {
    private Long id;
    private String title;
    private String message;
    private String status;
    private LocalDateTime creationDate;
    private String authorName;
    private String courseName;
}
