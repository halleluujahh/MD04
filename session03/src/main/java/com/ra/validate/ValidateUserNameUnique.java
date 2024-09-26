package com.ra.validate;

import com.ra.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateUserNameUnique implements ConstraintValidator<UserUnique,String> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return !userRepository.existsUserByUserName(userName);
    }
}
