package com.ra.model.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String userName;
    private String address;
}
