package com.cozyhaven.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;

    // Comma-separated amenities for simplicity
    private String amenities;

    // 🔗 Link to Owner (User with role OWNER or ADMIN)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();
}
