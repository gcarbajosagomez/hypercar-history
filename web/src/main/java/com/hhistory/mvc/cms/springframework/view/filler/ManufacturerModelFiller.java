package com.hhistory.mvc.cms.springframework.view.filler;

import com.hhistory.data.dao.sql.SqlManufacturerRepository;
import com.hhistory.mvc.dto.PaginationDTO;
import com.hhistory.mvc.springframework.view.filler.ModelFiller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.hhistory.mvc.cms.controller.CMSBaseController.*;
import static com.hhistory.mvc.controller.BaseControllerData.PAG_NUM_DATA;

/**
 * Fills a Spring Framework Model with manufacturer related information
 *
 * @author gonzalo
 */
@AllArgsConstructor(onConstructor = @__(@Inject))
@Component
public class ManufacturerModelFiller implements ModelFiller {

    private SqlManufacturerRepository manufacturerRepository;

    @Override
    public Model fillModel(Model model) {
        model.addAttribute(MANUFACTURER_ENTITIES, this.manufacturerRepository.findAll());
        return model;
    }

    /**
     * Fill the model with paginated manufacturer data
     *
     * @param model
     * @param manufacturersPaginationDTO
     */
    public Model fillPaginatedModel(Model model, PaginationDTO manufacturersPaginationDTO) {
        model.addAttribute(MANUFACTURERS_PER_PAGE_DATA, manufacturersPaginationDTO.getItemsPerPage());
        model.addAttribute(PAG_NUM_DATA, manufacturersPaginationDTO.getPagNum());
        model.addAttribute(MANUFACTURERS_PER_PAGE, MANUFACTURERS_PER_PAGE);
        model = this.fillModel(model);
        return model;
    }
}
