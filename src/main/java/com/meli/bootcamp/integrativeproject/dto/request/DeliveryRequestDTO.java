package com.meli.bootcamp.integrativeproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DeliveryRequestDTO {
    private String address;
    private Integer addressNumber;
    private Long cart_id;
    private Long buyer_id;
    private Double weight;

}
