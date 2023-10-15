package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GroupMembership{

    @NotNull
    private Integer id; // getId
    @NotNull
    private Integer grpId; // getGrpId
    @NotNull
    private Integer memberId;
    @NotNull
    private String memberRole;
}
