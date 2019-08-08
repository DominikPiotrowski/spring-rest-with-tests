package com.dominikpiotrowski.springrest.demo.dao.Entity.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieListDataTransfer {
    List<MovieDataTransfer> movieDataTransfersList;
}
