package org.com.session06.repository;

import org.com.session06.entity.Role;
import org.com.session06.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Page<User> findUserByStatusAndRoles(Pageable pageable, Boolean status, Set<Role> roles);
    Page<User> findUserByStatusAndRolesAndUsernameContaining(Pageable pageable, Boolean status, Set<Role> roles, String keyword);
}
