package com.ra.service.user;

import com.ra.model.dto.user.UserRequestDTO;
import com.ra.model.dto.user.UserResponseDTO;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImp implements UserService{
//    @Autowired
//    private UserRepository userRepository;
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<UserResponseDTO> findAll() {
        return null;
    }
    @Override
    public UserResponseDTO save(UserRequestDTO requestDTO) {
        // convert DTO => Entty

        User user = User.builder()
                .userName(requestDTO.getUserName()).
                fullName(requestDTO.getFullName()).
                password(requestDTO.getPassword()).
                address(requestDTO.getAddress())
                .build();
        // goij den repository
        User userNew = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(userNew.getId())
                .userName(userNew.getUserName()).
                fullName(userNew.getFullName())
                .address(userNew.getAddress())
                .build();
    }

    @Override
    public UserResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
