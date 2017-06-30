package com.phistory.mvc.service;

import com.phistory.data.model.Manufacturer;
import org.springframework.ui.Model;

import java.util.Optional;

/**
 * Created by Gonzalo Carbajosa on 23/05/17.
 */
public interface ManufacturerService {

    Optional<Manufacturer> mapToInMemoryEntity(Model model);

    Optional<Manufacturer> getInMemoryEntityFromModel(Model model);

    com.phistory.mvc.manufacturer.Manufacturer getFromModel(Model model);
}
