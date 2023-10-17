package com.example.hostelmanagement.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Inventory {
    @NotNull
    private Long itemId;

    @NotNull
    private String itemName;

    @NotNull
    private int quantity;

    private Integer thresholdQuantity;
}
