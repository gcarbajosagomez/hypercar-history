package com.hhistory.mvc.cms.service.impl;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.mvc.dto.CarPaginationDTO;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.service.impl.CarPaginationServiceImpl;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;
import java.util.Optional;

import static com.hhistory.mvc.cms.service.impl.CmsCarPaginationServiceImpl.CMS_CAR_PAGINATION_SERVICE_IMPL;
import static com.hhistory.mvc.cms.springframework.view.filler.CmsInMemoryCarListModelFiller.CMS_IN_MEMORY_CAR_LIST_MODEL_FILLER;

/**
 * Created by Gonzalo Carbajosa on 8/08/17.
 */
@Component(CMS_CAR_PAGINATION_SERVICE_IMPL)
public class CmsCarPaginationServiceImpl extends CarPaginationServiceImpl {

    public static final String CMS_CAR_PAGINATION_SERVICE_IMPL = "cmsCarPaginationServiceImpl";

    private InMemoryCarDAO      inMemoryCarDAO;
    private ManufacturerService manufacturerService;

    @Inject
    public CmsCarPaginationServiceImpl(
            @Named(CMS_IN_MEMORY_CAR_LIST_MODEL_FILLER) AbstractCarListModelFiller inMemoryCarListModelFiller,
            ManufacturerService manufacturerService,
            InMemoryCarDAO inMemoryCarDAO,
            MessageSource messageSource) {
        super(inMemoryCarListModelFiller, manufacturerService, inMemoryCarDAO, messageSource);
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public CarPaginationDTO paginate(Model model, CarPaginationDTO paginationDTO) {
        super.paginate(model, paginationDTO);
        CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                      .build();
        paginationDTO.getManufacturer()
                     .map(manufacturer -> {
                         Optional<Manufacturer> manufacturerEntityOptional =
                                 this.manufacturerService.mapToInMemoryEntity(manufacturer);

                         Manufacturer manufacturerEntity = manufacturerEntityOptional.orElse(null);
                         queryCommand.setManufacturer(manufacturerEntity);
                         return manufacturer;
                     });

        List<Car> models = this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand);
        paginationDTO.setModels(models);

        return paginationDTO;
    }
}
