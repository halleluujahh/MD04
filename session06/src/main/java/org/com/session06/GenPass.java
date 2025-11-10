package org.com.session06;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class GenPass {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        UUID uuid = UUID.randomUUID();
        System.out.println("Generated UUID from name: " + uuid.toString());
    }
}
