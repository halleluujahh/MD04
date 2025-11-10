package org.com.session06.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.session06.dto.request.UserInfoRequestDTO;
import org.com.session06.dto.response.UserInfoResponseDTO;
import org.com.session06.entity.Address;
import org.com.session06.entity.User;
import org.com.session06.exception.BadRequestException;
import org.com.session06.repository.AddressRepository;
import org.com.session06.repository.UserRepository;
import org.com.session06.service.UploadFileService;
import org.com.session06.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UploadFileService uploadFileService;

    @Override
    public UserInfoResponseDTO getUserInformation(Long userId) {
        User user = userRepository.findUserById(userId);
        return UserInfoResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .fullname(user.getFullName())
                .address(user.getAddress())
                .build();
    }

    @Override
    public UserInfoResponseDTO updateProfile(UserInfoRequestDTO userInfoRequestDTO) throws BadRequestException {
        String imageUrl = "";
        if (userInfoRequestDTO.getImage() != null) {
            imageUrl = uploadFileService.uploadFileToLocal(userInfoRequestDTO.getImage());
        }
        String[] cityArrayStr = userInfoRequestDTO.getCity().split("-");
        String[] districtArrayStr = userInfoRequestDTO.getDistrict().split("-");
        String[] streetArrayStr = userInfoRequestDTO.getStreet().split("-");
        Address address = addressRepository.findAddressByUser(userRepository.findUserById(userInfoRequestDTO.getUserId()));
        if (address == null){
            User userToUpdate = userRepository.findUserById(userInfoRequestDTO.getUserId());
            if(!imageUrl.isEmpty()){
                userToUpdate.setAvatar(imageUrl);
            }
            userToUpdate.setUsername(userInfoRequestDTO.getUsername());
            userToUpdate.setFullName(userInfoRequestDTO.getFullname());
            userToUpdate.setPhone(userInfoRequestDTO.getPhone());
            User userUpdated = userRepository.save(userToUpdate);
            address = Address.builder()
                    .city(cityArrayStr[0])
                    .provinceId(Integer.parseInt(cityArrayStr[1]))
                    .district(districtArrayStr[0])
                    .districtId(Integer.parseInt(districtArrayStr[1]))
                    .street(streetArrayStr[0])
                    .wardCode(streetArrayStr[1])
                    .specify(userInfoRequestDTO.getSpecify())
                    .user(userUpdated)
                    .build();
            Address addressCreated = addressRepository.save(address);
            return UserInfoResponseDTO.builder()
                    .username(userUpdated.getUsername())
                    .avatar(userUpdated.getAvatar())
                    .email(userUpdated.getEmail())
                    .phone(userUpdated.getPhone())
                    .fullname(userUpdated.getFullName())
                    .address(addressCreated)
                    .build();
        }
        address.setCity(cityArrayStr[0]);
        address.setProvinceId(Integer.parseInt(cityArrayStr[1]));
        address.setDistrict(districtArrayStr[0]);
        address.setDistrictId(Integer.parseInt(districtArrayStr[1]));
        address.setStreet(streetArrayStr[0]);
        address.setWardCode(streetArrayStr[1]);
        address.setSpecify(userInfoRequestDTO.getSpecify());
        Address addressUpdated = addressRepository.save(address);
        User userToUpdate = userRepository.findUserById(userInfoRequestDTO.getUserId());
        if(!imageUrl.isEmpty()){
            userToUpdate.setAvatar(imageUrl);
        }
        userToUpdate.setUsername(userInfoRequestDTO.getUsername());
        userToUpdate.setFullName(userInfoRequestDTO.getFullname());
        userToUpdate.setPhone(userInfoRequestDTO.getPhone());
        User userUpdated = userRepository.save(userToUpdate);
        return UserInfoResponseDTO.builder()
                .username(userUpdated.getUsername())
                .avatar(userUpdated.getAvatar())
                .email(userUpdated.getEmail())
                .phone(userUpdated.getPhone())
                .fullname(userUpdated.getFullName())
                .address(addressUpdated)
                .build();
    }
}
