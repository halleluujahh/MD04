package org.com.session06.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderDetailsId.class)
public class OrderDetails {
    @Id
    private Long orderId;
    @Id
    private Long productId;
    @Column(columnDefinition = "varchar(100)")
    private String name;
    @Column(columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    private Integer orderQuantity;
}
