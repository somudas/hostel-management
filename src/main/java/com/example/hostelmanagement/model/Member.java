package com.example.hostelmanagement.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.sql.Date;

@Data
public class Member {
    @Getter
    @NotNull
    private Integer mid;

    @Getter
    @NotNull
    private String role;

    @Getter
    @Size(min=1, max=15)
    @NotNull
    private String firstname;


    @Size(max=20)
    @Nullable
    private String lastname;

    // TODO: potential bug -> what happens when year>2050?
    @Getter
    @Range(min=1919, max=2050)
    @NotNull
    private Integer batch;

    @Getter
    @NotNull
    private String branch;

    @Getter
    @NotNull
    private java.sql.Date dateofbirth;

    @Getter
    @NotNull
    @Email
    @Size(max=100)
    private String email;

    @Getter
    @Size(min=10, max=10)
    @NotNull
    private String phonenumber;


}
