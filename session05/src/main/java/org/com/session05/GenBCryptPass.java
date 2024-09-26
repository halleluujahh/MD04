package org.com.session05;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenBCryptPass {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
