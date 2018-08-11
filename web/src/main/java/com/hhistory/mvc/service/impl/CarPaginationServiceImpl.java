package com.hhistory.mvc.service.impl;

import com.hhistory.data.command.CarQueryCommand;
import com.hhistory.data.dao.inmemory.InMemoryCarDAO;
import com.hhistory.data.model.GenericEntity;
import com.hhistory.mvc.dto.CarPaginationDTO;
import com.hhistory.mvc.service.CarPaginationService;
import com.hhistory.mvc.service.ManufacturerService;
import com.hhistory.mvc.springframework.view.filler.AbstractCarListModelFiller;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.List;

import static com.hhistory.mvc.controller.BaseControllerData.CARS;

/**
 * Created by Gonzalo Carbajosa on 7/08/17.
 */
@AllArgsConstructor(onConstructor = @__(@Inject))
@Component
@Primary
public class CarPaginationServiceImpl implements CarPaginationService {

    private static final String CAR_LIST_PAGE_TITLE_MESSAGE_KEY = "meta.title.allModels";

    private AbstractCarListModelFiller inMemoryCarListModelFiller;
    private ManufacturerService        manufacturerService;
    private InMemoryCarDAO             inMemoryCarDAO;
    private MessageSource              messageSource;

    @Override
    public CarPaginationDTO paginate(Model model, CarPaginationDTO paginationDTO) {
        model = this.inMemoryCarListModelFiller.fillPaginatedModel(model, paginationDTO);

        paginationDTO.setItems((List<GenericEntity>) model.asMap().get(CARS));

        this.manufacturerService.getInMemoryEntityFromModel(model)
                                .ifPresent(manufacturer -> {
                                    CarQueryCommand queryCommand = CarQueryCommand.builder()
                                                                                  .manufacturer(manufacturer)
                                                                                  .build();

                                    Object[] args = new Object[] {
                                            this.inMemoryCarDAO.getByQueryCommandOrderedByProductionStartDate(queryCommand).size(),
                                            paginationDTO.getFirstResult() + 1,
                                            paginationDTO.getLastResult()
                                    };

                                    String pageTitle = this.messageSource
                                                            .getMessage(manufacturer.getName().toLowerCase() + "." +
                                                                        CAR_LIST_PAGE_TITLE_MESSAGE_KEY,
                                                                        args,
                                                                        LocaleContextHolder.getLocale());

                                    paginationDTO.setPageTitle(pageTitle);
                                });

        return paginationDTO;
    }
}
