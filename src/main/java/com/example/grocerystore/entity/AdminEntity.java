package com.example.grocerystore.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="admin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="AdminName")
    private String adminname;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

}