package org.com.session02.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "category_status")
    private Boolean categoryStatus;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    Set<Product> listProduct;
}
