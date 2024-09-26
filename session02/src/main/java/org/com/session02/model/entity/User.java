package org.com.session02.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    @Column(name = "user_name", nullable = false, unique = true, length = 100)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "address")
    private String address;
}