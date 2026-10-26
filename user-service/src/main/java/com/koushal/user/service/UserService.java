package com.koushal.user.service;

import com.koushal.user.dto.UserRegistrationRequestDto;
import com.koushal.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRegistrationRequestDto requestDto);

}
