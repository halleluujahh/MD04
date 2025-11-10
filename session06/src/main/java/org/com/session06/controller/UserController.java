package org.com.session06.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.com.session06.dto.request.ProductRequestDTO;
import org.com.session06.dto.request.UserInfoRequestDTO;
import org.com.session06.exception.BadRequestException;
import org.com.session06.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("information/{userId}")
    public ResponseEntity<?> getInformation(
            @PathVariable(value = "userId", required = true) Long userId
    ) {
        return new ResponseEntity<>(userService.getUserInformation(userId), HttpStatus.OK);
    }

    @PostMapping("updateInformation")
    public ResponseEntity<?> updateInformation(
            @Valid @ModelAttribute UserInfoRequestDTO userInfoRequestDTO, BindingResult bindingResult
    ) throws BadRequestException {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMessages = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorMessages.put(fieldName, message);
            });
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return new ResponseEntity<>(userService.updateProfile(userInfoRequestDTO), HttpStatus.CREATED);
    }
}
