package com.cozyhaven.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    private int rating; // 1-5
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
