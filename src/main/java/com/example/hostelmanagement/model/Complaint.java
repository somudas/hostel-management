package com.example.hostelmanagement.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Data
public class Complaint {
    @NotNull
    private Integer cmpId;

    @Size(min=1, max=50)
    @NotNull
    private String title;

    @Size(min=1, max=250)
    @NotNull
    private String description;

    @Size(min=1, max=50)
    private String feedback;

    @NotNull
    private java.sql.Date postedAt;

    @NotNull
    private complaintStatus status;

    @NotNull
    private Integer postedById;

    @NotNull
    private String postedByRole;
}