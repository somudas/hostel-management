package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Message extends Member{

    private Integer msgId;
    private Integer grpId;
    @Size(min=1, max=250)
    private String content;
    private Integer sentById;
    private String sentByRole;
    private java.sql.Timestamp sentAt;

}
