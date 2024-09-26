package com.ra.service.user;

import com.ra.model.dto.user.UserRequestDTO;
import com.ra.model.dto.user.UserResponseDTO;
import com.ra.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    UserResponseDTO save(UserRequestDTO requestDTO);
    UserResponseDTO findById(Long id);
    void deleteById(Long id);
}
