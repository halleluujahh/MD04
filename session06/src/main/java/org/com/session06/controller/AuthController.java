package org.com.session06.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.com.session06.dto.UserLoginDTO;
import org.com.session06.dto.UserRegisterDTO;
import org.com.session06.exception.BadRequestException;
import org.com.session06.exception.NotFoundException;
import org.com.session06.exception.UnauthorizeException;
import org.com.session06.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult bindingResult) throws UnauthorizeException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(authService.login(userLoginDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) throws BadRequestException, MessagingException, UnsupportedEncodingException {
        if(bindingResult.hasErrors()){
                Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(authService.register(userRegisterDTO),HttpStatus.CREATED);
    }
    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestBody UserRegisterDTO userRegisterDTO) throws NotFoundException, BadRequestException {
        return new ResponseEntity<>(authService.verifyAccount(userRegisterDTO), HttpStatus.OK);
    }
}
