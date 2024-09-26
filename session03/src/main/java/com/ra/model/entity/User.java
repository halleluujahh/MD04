package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name",nullable = false,length = 100)
    private String fullName;
    @Column(name = "user_name",nullable = false,unique = true,length = 100)
    private String userName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "address")
    private String address;


}
