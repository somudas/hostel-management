package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Dues {
    @NotNull
    private Integer dueId;
    @NotNull
    private Integer imposedOnId;
    @NotNull
    private String imposedOnRole;
    @NotNull
    private java.sql.Date dueDate;
    @NotNull
    private String dueType;
    @NotNull
    private int dueAmount;
}
