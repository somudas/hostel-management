package com.example.hostelmanagement.model;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class LoginForm {
    private String role;
    private String insti_id;
    private String password;
}
