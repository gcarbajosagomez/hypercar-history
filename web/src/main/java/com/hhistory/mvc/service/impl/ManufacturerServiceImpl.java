package com.hhistory.mvc.service.impl;

import com.hhistory.data.dao.inmemory.InMemoryManufacturerDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.mvc.service.ManufacturerService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.Optional;

import static com.hhistory.mvc.controller.BaseControllerData.MANUFACTURER;
import static com.hhistory.mvc.controller.BaseControllerData.MANUFACTURER_ENTITY;

/**
 * Created by Gonzalo Carbajosa on 23/05/17.
 */
@Component
public class ManufacturerServiceImpl implements ManufacturerService {

    private InMemoryManufacturerDAO inMemoryManufacturerDAO;

    @Inject
    public ManufacturerServiceImpl(InMemoryManufacturerDAO inMemoryManufacturerDAO) {
        this.inMemoryManufacturerDAO = inMemoryManufacturerDAO;
    }

    @Override
    public Optional<Manufacturer> mapToInMemoryEntity(Model model) {
        com.hhistory.mvc.manufacturer.Manufacturer manufacturer =
                (com.hhistory.mvc.manufacturer.Manufacturer) model.asMap().get(MANUFACTURER);

        return this.inMemoryManufacturerDAO.getByName(manufacturer.getName());
    }

    @Override
    public Optional<Manufacturer> getInMemoryEntityFromModel(Model model) {
        return Optional.ofNullable((Manufacturer) model.asMap().get(MANUFACTURER_ENTITY));
    }

    @Override
    public com.hhistory.mvc.manufacturer.Manufacturer getFromModel(Model model) {
        return (com.hhistory.mvc.manufacturer.Manufacturer) model.asMap().get(MANUFACTURER);
    }
}
