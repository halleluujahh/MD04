package org.com.session06.dto.response;
import lombok.*;
import org.com.session06.entity.Role;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO implements Serializable {
    private String userName;
    private String token;
    private String typeToken;
    private Set<Role> roles;
    private Long userId;
}
