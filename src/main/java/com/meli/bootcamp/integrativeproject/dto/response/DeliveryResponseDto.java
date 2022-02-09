package com.meli.bootcamp.integrativeproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDto {
    private String nameBuyer;
    private Long cart_id;
    private String status_delivery;
    private String trackingCode;

    }