package com.forumhub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @PrePersist
    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
        this.status = TopicStatus.NAO_RESPONDIDO;
    }
}
