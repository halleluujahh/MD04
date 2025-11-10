package org.com.session06.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterDTO {
    @NotBlank(message = "Username không được để trống")
    private String username;
    @NotBlank(message = "Email không được để trống")
    @Email
    private String email;
    @NotBlank(message = "Password không được để trống")
    @Length(min = 6, message = "Password phải từ 6 kí tự trở lên")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "Password phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số")
    private String password;
    @NotBlank(message = "Re-Password không được để trống")
    private String repassword;
    private String verifyCode;
}