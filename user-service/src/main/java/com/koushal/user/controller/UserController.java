package com.koushal.user.controller;

import com.koushal.user.dto.LoginRequestDto;
import com.koushal.user.dto.LoginResponseDto;
import com.koushal.user.dto.UserRegistrationRequestDto;
import com.koushal.user.dto.UserResponseDto;
import com.koushal.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto) {

        LoginResponseDto response = userService.login(requestDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(Authentication authentication) {

        return ResponseEntity.ok(
                Map.of("email", authentication.getName())
        );
    }
}

