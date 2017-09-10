package com.hhistory.mvc.dto;

import com.hhistory.data.model.car.Car;
import lombok.Data;

import java.util.List;

/**
 * Created by Gonzalo Carbajosa on 8/08/17.
 */
@Data
public class CarPaginationDTO extends PaginationDTO {

    private List<Car> models;
}
