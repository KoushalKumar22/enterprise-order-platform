package com.koushal.user.controller;

import com.koushal.user.dto.UserRegistrationRequestDto;
import com.koushal.user.dto.UserResponseDto;
import com.koushal.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @Valid @RequestBody UserRegistrationRequestDto requestDto) {

        UserResponseDto response = userService.registerUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

