package org.com.session06.security;

import lombok.SneakyThrows;
import org.com.session06.entity.User;
import lombok.AllArgsConstructor;
import org.com.session06.exception.NotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.com.session06.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại!"));

        UserPrinciple userPrinciple = new UserPrinciple();

        userPrinciple.setUser(user);
        userPrinciple.setAuthorities(user.getRoles().stream().map(
                a -> new SimpleGrantedAuthority(a.getName())
        ).collect(Collectors.toSet()));

        return userPrinciple;
    }
}
