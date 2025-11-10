package org.com.session06.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.com.session06.entity.Category;

import java.math.BigDecimal;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartProductResponseDTO {
    private Long productId;
    private String sku;
    private String productName;
    private String description;
    private BigDecimal unitPrice;
    private String image;
    @JsonIgnore
    private Category category;
    private Integer quantity;

    @JsonProperty("categoryId")
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }
}
