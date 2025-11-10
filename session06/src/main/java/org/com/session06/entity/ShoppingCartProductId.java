package org.com.session06.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartProductId implements Serializable {
    private Long shoppingCartId;
    private Long productId;
}
