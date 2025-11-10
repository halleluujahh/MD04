package org.com.session06.service;

import jakarta.mail.MessagingException;
import org.com.session06.dto.*;
import org.com.session06.dto.request.AccountRequestDTO;
import org.com.session06.dto.response.UserRegisterResponseDTO;
import org.com.session06.dto.response.UserResponseDTO;
import org.com.session06.entity.User;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.exception.UnauthorizeException;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuthService {
    UserResponseDTO login(UserLoginDTO userLoginDTO) throws UnauthorizeException;
    UserRegisterResponseDTO register(UserRegisterDTO userRegisterDTO) throws BadRequestException, MessagingException, UnsupportedEncodingException;
    UserRegisterResponseDTO verifyAccount(UserRegisterDTO userRegisterDTO) throws NotFoundException, BadRequestException;
    List<User> findAll();
    Page<User> findAll(Integer page, Integer limit, Boolean status, String sort, String keyword);
    UserResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    User changeStatus(Long userId) throws NotFoundException;
}
