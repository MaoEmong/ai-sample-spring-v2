package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class Join {
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @Size(min = 4, max = 20, message = "아이디는 4자에서 20자 사이여야 합니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4자에서 20자 사이여야 합니다.")
        private String password;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
    }

    @Data
    public static class Login {
        private String username;
        private String password;
    }

}
