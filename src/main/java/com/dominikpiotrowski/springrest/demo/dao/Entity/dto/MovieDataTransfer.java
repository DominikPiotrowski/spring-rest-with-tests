package com.dominikpiotrowski.springrest.demo.dao.Entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDataTransfer {

    private Long id;
    private String title;
    private Year productionYear;
    private String maker;
}