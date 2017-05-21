package com.phistory.mvc.cms.controller.util;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlDAO;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.Manufacturer;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.dto.PaginationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.phistory.data.dao.sql.SqlManufacturerDAO.SQL_MANUFACTURER_DAO;
import static com.phistory.data.model.Manufacturer.NAME_PROPERTY;
import static com.phistory.mvc.cms.command.EntityManagementQueryType.RELOAD_MANUFACTURERS;

/**
 * Set of utilities for the ManufacturerController class
 *
 * @author gonzalo
 */
@Component
@Slf4j
public class CMSManufacturerControllerUtil {

    private SqlDAO                  sqlManufacturerDAO;
    private EntityManagementService entityManagementService;

    @Inject
    public CMSManufacturerControllerUtil(@Named(SQL_MANUFACTURER_DAO) SqlDAO sqlManufacturerDAO,
                                         EntityManagementService entityManagementService) {
        this.sqlManufacturerDAO = sqlManufacturerDAO;
        this.entityManagementService = entityManagementService;
    }

    /**
     * Create the data needed to handle manufacturer pagination
     *
     * @param paginationDTO
     * @return
     */
    public PaginationDTO createPaginationData(PaginationDTO paginationDTO) {
        List<GenericEntity> manufacturers = this.sqlManufacturerDAO.getByCriteria(this.createSearchCommand(paginationDTO));
        paginationDTO.setItems(manufacturers);

        return paginationDTO;
    }

    /**
     * Create a search command to search for manufacturers
     *
     * @param manufacturersPaginationDTO
     * @return
     */
    private SearchCommand createSearchCommand(PaginationDTO manufacturersPaginationDTO) {
        Map<String, Boolean> orderByMap = new HashMap<>();
        orderByMap.put(NAME_PROPERTY, true);

        int paginationFirstResult = manufacturersPaginationDTO.getFirstResult();

        return SearchCommand.builder()
                            .entityClass(Manufacturer.class)
                            .orderByMap(orderByMap)
                            .firstResult(paginationFirstResult)
                            .maxResults(manufacturersPaginationDTO.getItemsPerPage())
                            .build();
    }

    public void reloadManufacturerDBEntities(Long manufacturerId) {
        EntityManagementLoadCommand entityManagementLoadCommand = new EntityManagementLoadCommand();
        entityManagementLoadCommand.setManufacturerId(manufacturerId);
        entityManagementLoadCommand.setQueryType(RELOAD_MANUFACTURERS);
        this.entityManagementService.reloadEntities(entityManagementLoadCommand);
    }
}
