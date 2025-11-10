package org.com.session06.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequestDTO {
    private Long productId;
    @NotBlank(message = "Tên sản phẩm không được để trống.")
    private String productName;
    @Min(value = 1, message = "Giá phải lớn hơn 0.")
    private BigDecimal price;
    @Min(value = 1, message = "Số lượng phải lớn hơn 0.")
    private Integer stock;
    @NotBlank(message = "Tiêu đề không được để trống.")
    private String description;
    private Long categoryId;
    private MultipartFile image;
}
