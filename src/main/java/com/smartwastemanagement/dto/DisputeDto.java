package com.smartwastemanagement.dto;

import lombok.Data;

@Data
public class DisputeDto {

    private Integer userId;
    private Integer paymentId;
    private String reason;


}
