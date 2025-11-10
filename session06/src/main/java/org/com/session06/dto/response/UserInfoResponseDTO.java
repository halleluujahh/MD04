package org.com.session06.dto.response;

import lombok.*;
import org.com.session06.entity.Address;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInfoResponseDTO {
    private String username;
    private String avatar;
    private String email;
    private String phone;
    private String fullname;
    private Address address;
}
