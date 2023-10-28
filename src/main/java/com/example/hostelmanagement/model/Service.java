package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Service {
    @NotNull
    private int serviceId;

    @NotNull
    private String serviceName;

    @NotNull
    private Integer assignedToId;
    @NotNull
    private String assignedToRole;

    private String firstname;
    private String lastname;

    private java.sql.Timestamp lastUpdatedOn;


}
