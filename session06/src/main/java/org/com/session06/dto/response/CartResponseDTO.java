package org.com.session06.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.com.session06.entity.Category;
import org.com.session06.entity.Product;
import org.com.session06.entity.ShoppingCartProduct;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartResponseDTO {
    private Long cartId;
    private List<CartProductResponseDTO> cartItems;
    private Long userId;
}
