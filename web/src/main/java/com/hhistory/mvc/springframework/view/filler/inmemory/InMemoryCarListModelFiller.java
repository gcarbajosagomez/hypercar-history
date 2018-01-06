package com.hhistory.mvc.springframework.view.filler.inmemory;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.car.Car;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hhistory.mvc.cms.controller.CMSBaseController.MANUFACTURERS;
import static com.hhistory.mvc.controller.BaseControllerData.CARS;
import static com.hhistory.mvc.controller.BaseControllerData.MODELS;
import static com.hhistory.mvc.springframework.view.filler.inmemory.InMemoryCarListModelFiller.IN_MEMORY_CAR_LIST_MODEL_FILLER;

/**
 * In-memory implementation of a {@link AbstractCarListModelFiller}
 *
 * @author gonzalo
 */
@AllArgsConstructor(onConstructor = @__({ @Inject }))
@Component(IN_MEMORY_CAR_LIST_MODEL_FILLER)
public class InMemoryCarListModelFiller extends AbstractCarListModelFiller {

    public static final String IN_MEMORY_CAR_LIST_MODEL_FILLER = "inMemoryCarListModelFiller";

    private InMemoryCarDAO      inMemoryCarDAO;
    private ManufacturerService manufacturerService;

    @Override
    public Model fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        super.fillPaginatedModel(model, paginationDTO);
        model.addAttribute(MANUFACTURERS, com.hhistory.mvc.manufacturer.Manufacturer.values());
        Optional<Manufacturer> manufacturerOptional = this.manufacturerService.getInMemoryEntityFromModel(model);
        manufacturerOptional.ifPresent(manufacturer -> {
            CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                          .visible(true)
                                                          .manufacturer(manufacturer)
                                                          .build();

            model.addAttribute(CARS,
                               this.loadCarsByPaginationDTO(paginationDTO, queryCommand));

            model.addAttribute(MODELS, this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand));
        });

        return model;
    }

    /**
     * Get a {@link List<Car>} paginated and ordered by {@link Car#productionStartDate} desc
     *
     * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
    protected List<Car> loadCarsByPaginationDTO(PaginationDTO paginationDTO, CarQueryCommand queryCommand) {
        return this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand)
                                  .stream()
                                  .skip(paginationDTO.getFirstResult())
                                  .limit(paginationDTO.getItemsPerPage())
                                  .collect(Collectors.toList());
    }
}
