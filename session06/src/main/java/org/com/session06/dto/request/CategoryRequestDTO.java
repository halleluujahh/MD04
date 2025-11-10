package org.com.session06.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequestDTO {
    private Long categoryId;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String categoryName;
    @NotBlank(message = "Tiêu đề không được để trống")
    private String description;
    private Boolean status;
}
