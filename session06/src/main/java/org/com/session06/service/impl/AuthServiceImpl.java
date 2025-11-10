package org.com.session06.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.com.session06.dto.*;
import org.com.session06.dto.request.AccountRequestDTO;
import org.com.session06.dto.response.UserRegisterResponseDTO;
import org.com.session06.dto.response.UserResponseDTO;
import org.com.session06.entity.Role;
import org.com.session06.entity.User;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.exception.UnauthorizeException;
import org.com.session06.helper.OTPGenerator;
import org.com.session06.repository.RoleRepository;
import org.com.session06.repository.UserRepository;
import org.com.session06.security.UserPrinciple;
import org.com.session06.security.jwt.JwtProvider;
import org.com.session06.service.AuthService;
import org.com.session06.service.MailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationProvider authenticationProvider;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final OTPGenerator otpGenerator;
    private final MailService mailService;

    @Override
    public UserResponseDTO login(UserLoginDTO userLoginDTO) throws UnauthorizeException {
        try {
            Authentication authentication;
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(), userLoginDTO.getPassword()));
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return UserResponseDTO.builder().
                    userName(userPrinciple.getUsername()).
                    token(jwtProvider.generateToken(userPrinciple)).
                    typeToken("Bearer")
                    .roles(userPrinciple.getUser().getRoles())
                    .userId(userPrinciple.getUser().getId())
                    .build();
        } catch (Exception ex) {
            throw new UnauthorizeException("Sai thông tin đăng nhập hoặc mật khẩu!");
        }
    }

    @Override
    public UserRegisterResponseDTO register(UserRegisterDTO userRegisterDTO) throws BadRequestException, MessagingException, UnsupportedEncodingException {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepassword())) {
            throw new BadRequestException("Mật khẩu không khớp với mật khẩu nhập lại");
        }
        if (userRepository.getUserByUsername(userRegisterDTO.getUsername()).isPresent()) {
            throw new BadRequestException("Tài khoản đã tồn tại");
        }
        if (userRepository.getUserByUsername(userRegisterDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");
        }
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName("USER");
        roles.add(role);
        User user = User.builder().
                username(userRegisterDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRegisterDTO.getPassword()))
                .status(false)
                .email(userRegisterDTO.getEmail())
                .roles(roles)
                .verifyCode(otpGenerator.generateVerifyCode())
                .build();
        User userNew;
        try {
            mailService.sendVerifyCode(userRegisterDTO.getEmail(), user.getVerifyCode());
            userNew = userRepository.save(user);
        } catch (Exception ex) {
            throw new BadRequestException("Email address not exist.");
        }
        return UserRegisterResponseDTO.builder()
                .userName(userNew.getUsername())
                .email(userNew.getEmail())
                .build();
    }

    @Override
    public UserRegisterResponseDTO verifyAccount(UserRegisterDTO userRegisterDTO) throws NotFoundException, BadRequestException {
        User userToVerify = userRepository.getUserByUsername(userRegisterDTO.getUsername())
                .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại"));
        if (!userToVerify.getVerifyCode().equals(userRegisterDTO.getVerifyCode())) {
            throw new BadRequestException("Xác thực không thành công");
        }

        userToVerify.setStatus(true);
        userToVerify.setVerifyCode(null);
        User user = userRepository.save(userToVerify);
        UserRegisterResponseDTO userRegisterResponseDTO = UserRegisterResponseDTO.builder()
                .userName(user.getUsername())
                .fullName(user.getFullName()).build();
        return userRegisterResponseDTO;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Integer page, Integer limit, Boolean status, String sort, String keyword) {
        Sort sortAccount;
        Pageable pageable;
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName("USER");
        roles.add(role);
        if (sort != null) {
            sortAccount = Sort.by(Sort.Order.desc(sort));
            pageable = PageRequest.of(page, limit, sortAccount);
        } else {
            pageable = PageRequest.of(page, limit);
        }
        if(keyword != null){
            return userRepository.findUserByStatusAndRolesAndUsernameContaining(pageable, status, roles, keyword);
        }
        return userRepository.findUserByStatusAndRoles(pageable, status, roles);
    }

    @Override
    public UserResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : accountRequestDTO.getRoleName()) {
            Role role = roleRepository.findRoleByName(roleName);
            roles.add(role);
        }
        User user = User.builder()
                .username(accountRequestDTO.getUsername())
                .password(new BCryptPasswordEncoder().encode(accountRequestDTO.getPassword()))
                .roles(roles)
                .status(true)
                .build();

        User userNew = userRepository.save(user);

        return UserResponseDTO.builder()
                .userName(userNew.getUsername())
                .roles(userNew.getRoles())
                .build();
    }

    @Override
    public User changeStatus(Long userId) throws NotFoundException {
        User userToUpdateStatus = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Tài khoản với id %l không tồn tại", userId)));
        userToUpdateStatus.setStatus(!userToUpdateStatus.getStatus());
        userToUpdateStatus.setUpdatedAt(LocalDate.now());
        return userRepository.save(userToUpdateStatus);
    }
}
