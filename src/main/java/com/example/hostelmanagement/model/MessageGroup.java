package com.example.hostelmanagement.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class MessageGroup {

    private Integer grpId;

    @Size(min=1, max=50)
    private String name;
    @Size(min=1, max=250)
    private String description;

    private Integer adminId;
    private String adminRole;

    private List<Message> messages;
    private List<Member> members;

    private Integer unreadCnt;
}
