package com.jpo.pgearback.entity;

import com.jpo.pgearback.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_mdp")
    private String password;

    @Column(name="user_name")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private UserRole role;

    @Column(name = "user_points")
    private Integer points;

    @Lob
    @Column(columnDefinition = "longblob", name="user_image")
    private byte[] img;
}
