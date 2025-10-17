package com.smartwastemanagement.dto;

import lombok.Data;

@Data
public class RefundRequestDto {

    private Integer paymentId;
    private String reason;

}
