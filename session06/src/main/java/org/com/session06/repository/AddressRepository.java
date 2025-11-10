package org.com.session06.repository;

import org.com.session06.entity.Address;
import org.com.session06.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressByUser(User user);
}
