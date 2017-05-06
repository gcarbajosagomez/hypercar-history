package com.phistory.mvc.springframework.view.filler.inmemory;

import com.phistory.data.dao.inmemory.InMemoryCarDAO;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.dto.PaginationDTO;
import com.phistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.phistory.mvc.controller.BaseControllerData.CARS;

/**
 * In-memory implementation of a {@link AbstractCarListModelFiller}
 *
 * @author gonzalo
 */
@Component(value = "inMemoryCarsListModelFiller")
public class InMemoryCarsListModelFiller extends AbstractCarListModelFiller {
    @Inject
    private InMemoryCarDAO inMemoryCarDAO;

    @Override
    public Model fillPaginatedModel(Model model, PaginationDTO paginationDTO) {
        model = super.fillPaginatedModel(model, paginationDTO);
        model.addAttribute(CARS,
                           this.loadCarsBySearchCommand(paginationDTO));
        return model;
    }

    /**
     * Get a {@link List<Car>} paginated and ordered by {@link Car#productionStartDate} desc
     *
     * @param paginationDTO
     * @return The resulting {@link List<Car>}
     */
    public List<Car> loadCarsBySearchCommand(PaginationDTO paginationDTO) {
        return this.inMemoryCarDAO.getAllVisibleOrderedByProductionStartDate()
                                  .stream()
                                  .skip(paginationDTO.getFirstResult())
                                  .limit(paginationDTO.getItemsPerPage())
                                  .collect(Collectors.toList());
    }
}
