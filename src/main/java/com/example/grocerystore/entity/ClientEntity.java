package com.example.grocerystore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table(name="client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private Long clientId=0L;

    @Column(name="UserName")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy = "clientEntity",fetch = FetchType.LAZY)
    List<OrderEntity> orderEntities;

    @OneToMany(mappedBy = "clientEntity",fetch = FetchType.LAZY)
    List<CartEntity> cartEntities;
}
