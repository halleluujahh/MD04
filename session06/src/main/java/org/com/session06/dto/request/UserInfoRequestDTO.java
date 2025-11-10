package org.com.session06.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInfoRequestDTO {
    private Long userId;
    private MultipartFile image;
    @NotBlank
    private String username;
    @NotBlank
    private String fullname;
    @NotBlank
    private String phone;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    private String street;
    @NotBlank
    private String specify;
}
