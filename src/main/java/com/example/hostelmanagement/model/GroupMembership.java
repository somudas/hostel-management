package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GroupMembership{

    @NotNull
    private Integer id;
    @NotNull
    private Integer grpId;
    @NotNull
    private Integer memberId;
    @NotNull
    private String memberRole;
}
