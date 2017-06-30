package com.phistory.mvc.springframework.view.filler.inmemory;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.service.ManufacturerService;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.mvc.controller.BaseControllerData.CARS;
import static com.phistory.mvc.springframework.view.filler.inmemory.InMemoryCarListModelFiller.IN_MEMORY_CAR_LIST_MODEL_FILLER;

/**
 * In-memory implementation of a {@link AbstractCarListModelFiller}
 *
 * @author gonzalo
 */
@Component(IN_MEMORY_CAR_LIST_MODEL_FILLER)
public class InMemoryCarListModelFiller extends AbstractCarListModelFiller {

    public static final String IN_MEMORY_CAR_LIST_MODEL_FILLER = "inMemoryCarListModelFiller";

    private InMemoryCarDAO      inMemoryCarDAO;
    private ManufacturerService manufacturerService;

    @Inject
    public InMemoryCarListModelFiller(InMemoryCarDAO inMemoryCarDAO,
                                      ManufacturerService manufacturerService) {
        this.inMemoryCarDAO = inMemoryCarDAO;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Model fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        model = super.fillPaginatedModel(model, paginationDTO);
        model.addAttribute(CARS,
                           this.loadCarsBySearchCommand(paginationDTO, model));
        return model;
    }

    /**
     * Get a {@link List<Car>} paginated and ordered by {@link Car#productionStartDate} desc
     *
     * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
    private List<Car> loadCarsBySearchCommand(PaginationDTO paginationDTO, Model model) {
        List<Car> cars = new ArrayList<>();
        this.manufacturerService.getInMemoryEntityFromModel(model)
                                .ifPresent(manufacturer -> cars
                                        .addAll(this.inMemoryCarDAO.getAllVisibleOrderedByProductionStartDate(manufacturer)
                                                                   .stream()
                                                                   .skip(paginationDTO.getFirstResult())
                                                                   .limit(paginationDTO.getItemsPerPage())
                                                                   .collect(Collectors.toList())));
        return cars;
    }
}
