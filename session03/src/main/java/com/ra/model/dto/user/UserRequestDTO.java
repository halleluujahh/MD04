package com.ra.model.dto.user;

import com.ra.validate.UserUnique;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    @NotBlank(message = "Khong rong")
    private String fullName;
    @NotBlank
    @UserUnique(message = "User Name da ton tai vui long lay cai khac di")
    private String userName;
    @NotBlank
    private String password;
    private String address;
}
