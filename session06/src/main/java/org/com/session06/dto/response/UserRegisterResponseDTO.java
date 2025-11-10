package org.com.session06.dto.response;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterResponseDTO {
    private String fullName;
    private String userName;
    private String email;
}