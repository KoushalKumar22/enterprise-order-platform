package com.koushal.user.service;

import com.koushal.user.dto.LoginRequestDto;
import com.koushal.user.dto.LoginResponseDto;
import com.koushal.user.dto.UserRegistrationRequestDto;
import com.koushal.user.dto.UserResponseDto;
import com.koushal.user.entity.Role;
import com.koushal.user.entity.User;
import com.koushal.user.exception.DuplicateEmailException;
import com.koushal.user.exception.InvalidCredentialsException;
import com.koushal.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(
            UserRegistrationRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateEmailException("An account with this email already exists");
        }

        User user = modelMapper.map(requestDto, User.class);

        user.setId(null);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(Role.CUSTOMER);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword())) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDto(
                token,
                "Bearer",
                900L
        );
    }

}
