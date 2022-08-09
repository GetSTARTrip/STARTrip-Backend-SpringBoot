package com.startrip.core.dto;

import com.startrip.core.constant.Role;
import com.startrip.core.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private String nickname;

    // ..

    // ..
    public static User createUser(SignUpDto signUpDto) {
        User user = User.builder()
            .email(signUpDto.getEmail())
            .password(signUpDto.getPassword())
            .nickname(signUpDto.getNickname())
            .name(signUpDto.getName())
            .authorities(Role.USER)
            .build();
        return user;
    }
}
