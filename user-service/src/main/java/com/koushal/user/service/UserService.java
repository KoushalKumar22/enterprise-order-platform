package com.koushal.user.service;

import com.koushal.user.dto.LoginRequestDto;
import com.koushal.user.dto.LoginResponseDto;
import com.koushal.user.dto.UserRegistrationRequestDto;
import com.koushal.user.dto.UserResponseDto;

public interface UserService {


    LoginResponseDto login(LoginRequestDto requestDto);

    UserResponseDto registerUser(UserRegistrationRequestDto requestDto);

}
