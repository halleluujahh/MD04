package org.com.session06.service;

import org.com.session06.dto.request.UserInfoRequestDTO;
import org.com.session06.dto.response.UserInfoResponseDTO;
import org.com.session06.exception.BadRequestException;

public interface UserService {
    UserInfoResponseDTO getUserInformation(Long userId);
    UserInfoResponseDTO updateProfile(UserInfoRequestDTO userInfoRequestDTO) throws BadRequestException;
}
