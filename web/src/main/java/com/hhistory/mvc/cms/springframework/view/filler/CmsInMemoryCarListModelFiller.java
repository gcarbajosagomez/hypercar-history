package com.hhistory.mvc.cms.springframework.view.filler;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.springframework.view.filler.inmemory.InMemoryCarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hhistory.mvc.cms.controller.CMSBaseController.CMS_MODELS;
import static com.hhistory.mvc.cms.springframework.view.filler.CmsInMemoryCarListModelFiller.CMS_IN_MEMORY_CAR_LIST_MODEL_FILLER;
import static com.hhistory.mvc.controller.BaseControllerData.CARS;

/**
 * Created by Gonzalo Carbajosa on 7/08/17.
 */
@Component(CMS_IN_MEMORY_CAR_LIST_MODEL_FILLER)
public class CmsInMemoryCarListModelFiller extends InMemoryCarListModelFiller {

    public static final String CMS_IN_MEMORY_CAR_LIST_MODEL_FILLER = "cmsInMemoryCarListModelFiller";

    private ManufacturerService manufacturerService;
    private InMemoryCarDAO      inMemoryCarDAO;

    @Inject
    public CmsInMemoryCarListModelFiller(InMemoryCarDAO inMemoryCarDAO,
                                         ManufacturerService manufacturerService) {
        super(inMemoryCarDAO, manufacturerService);
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Model fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        super.fillPaginatedModel(model, paginationDTO);

        paginationDTO.getManufacturer()
                     .map(manufacturer -> {
                         Optional<Manufacturer> manufacturerEntityOptional =
                                 this.manufacturerService.mapToInMemoryEntity(manufacturer);

                         manufacturerEntityOptional.ifPresent(manufacturerEntity -> {
                             CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                                           .manufacturer(manufacturerEntity)
                                                                           .build();
                             model.addAttribute(CARS,
                                                super.loadCarsByPaginationDTO(paginationDTO, queryCommand));
                             model.addAttribute(CMS_MODELS,
                                                this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand));
                         });
                         return manufacturer;
                     })
                     .orElseGet(() -> {
                         model.addAttribute(CARS,
                                            this.loadCarsByPaginationDTO(paginationDTO));

                         CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                                       .build();
                         model.addAttribute(CMS_MODELS,
                                            this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand));
                         return null;
                     });

        return model;
    }

    /**
     * Get a {@link List < Car >} paginated and ordered by {@link Car#productionStartDate} desc
     *
     * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
    private List<Car> loadCarsByPaginationDTO(PaginationDTO paginationDTO) {
        CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                      .build();

        return this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand)
                                  .stream()
                                  .skip(paginationDTO.getFirstResult())
                                  .limit(paginationDTO.getItemsPerPage())
                                  .collect(Collectors.toList());
    }
}
