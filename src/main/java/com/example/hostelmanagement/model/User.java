package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
public class User {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String role;
}
