package org.com.session06.dto.request;

import lombok.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountRequestDTO {
    private String username;
    private String password;
    private Set<String> roleName;
}