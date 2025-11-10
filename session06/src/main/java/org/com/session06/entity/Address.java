package org.com.session06.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(100)")
    private String street;
    @Column(columnDefinition = "nvarchar(100)")
    private String district;
    @Column(columnDefinition = "nvarchar(100)")
    private String city;
    @Column(columnDefinition = "nvarchar(155)")
    private String specify;
    @Column(columnDefinition = "nvarchar(100)")
    private String wardCode;
    private Integer districtId;
    private Integer provinceId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}
