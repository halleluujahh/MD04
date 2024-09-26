package org.com.session02.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;
    @Column(name = "name")
    private String productName;
    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(columnDefinition = "categoryId", referencedColumnName = "id")
    private Category category;
}
