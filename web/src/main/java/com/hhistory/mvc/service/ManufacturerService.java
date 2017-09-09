package com.hhistory.mvc.service;

import com.hhistory.data.model.Manufacturer;
import org.springframework.ui.Model;

import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 23/05/17.
 */
public interface ManufacturerService {

    Optional<Manufacturer> mapToInMemoryEntity(Model model);

    Optional<Manufacturer> mapToInMemoryEntity(com.hhistory.mvc.manufacturer.Manufacturer manufacturer);

    Optional<Manufacturer> getInMemoryEntityFromModel(Model model);

    com.hhistory.mvc.manufacturer.Manufacturer getFromModel(Model model);
}
