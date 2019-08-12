package com.dominikpiotrowski.springrest.demo.dao.Entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDataTransfer {

    private Long id;
    private String title;
    private Integer production;
    private String maker;
}