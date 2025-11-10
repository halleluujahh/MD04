package org.com.session06.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 100)
    private String username;
    @Column(columnDefinition = "varchar(255)")
    private String email;
    private String password;
    @Column(columnDefinition = "varchar(255)", nullable = true)
    private String avatar;
    @Column(columnDefinition = "varchar(15)", unique = true)
    private String phone;
    private Boolean status;
    @Column(name = "verify_code")
    private String verifyCode;
    @Column(name = "fullname")
    private String fullName;
    @Column(name = "created_at", columnDefinition = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @Column(name = "updated_at", columnDefinition = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Order> orders;
    @OneToOne(mappedBy = "user")
    private ShoppingCart cart;
    @OneToOne(mappedBy = "user")
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}
