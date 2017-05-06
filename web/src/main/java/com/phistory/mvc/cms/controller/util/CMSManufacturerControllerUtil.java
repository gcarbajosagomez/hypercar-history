package com.phistory.mvc.cms.controller.util;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.dao.sql.SqlDAO;
import com.phistory.data.model.GenericEntity;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.car.Car;
import com.phistory.mvc.cms.command.EditFormCommand;
import com.phistory.mvc.cms.command.EntityManagementLoadCommand;
import com.phistory.mvc.cms.form.EditForm;
import com.phistory.mvc.cms.form.factory.EntityFormFactory;
import com.phistory.mvc.cms.service.EntityManagementService;
import com.phistory.mvc.dto.PaginationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.phistory.data.dao.sql.SqlManufacturerDAO.SQL_MANUFACTURER_DAO;
import static com.phistory.data.dao.sql.SqlManufacturerRepository.MANUFACTURER_REPOSITORY;
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

    private CrudRepository          sqlManufacturerRepository;
    private SqlDAO                  sqlManufacturerDAO;
    private EntityFormFactory       manufacturerFormFactory;
    private EntityManagementService entityManagementService;

    @Inject
    public CMSManufacturerControllerUtil(@Named(MANUFACTURER_REPOSITORY) CrudRepository sqlManufacturerRepository,
                                         @Named(SQL_MANUFACTURER_DAO) SqlDAO sqlManufacturerDAO,
                                         EntityFormFactory manufacturerFormFactory,
                                         EntityManagementService entityManagementService) {
        this.sqlManufacturerRepository = sqlManufacturerRepository;
        this.sqlManufacturerDAO = sqlManufacturerDAO;
        this.manufacturerFormFactory = manufacturerFormFactory;
        this.entityManagementService = entityManagementService;
    }

    /**
     * Handle the save or edition of a {@link Manufacturer}
     *
     * @param command
     * @return the newly saved or edited {@link Car} if everything went well, null otherwise
     * @throws Exception
     */
    public Optional<Manufacturer> saveOrEditManufacturer(EditFormCommand command) throws Exception {
        EditForm editForm = command.getEditForm();

        if (Objects.nonNull(editForm)) {
            Manufacturer manufacturer =
                    (Manufacturer) this.manufacturerFormFactory.buildEntityFromForm(editForm);
            log.info("Saving or editing manufacturer: {}", manufacturer.toString());
            manufacturer = (Manufacturer) this.sqlManufacturerRepository.save(manufacturer);

            if (editForm.getId() == null) {
                //After the car has been saved, we need to recreate the ManufacturerEditForm with all the newly assigned ids
                command.setEditForm(this.manufacturerFormFactory.buildFormFromEntity(manufacturer));
            }

            return Optional.of(manufacturer);
        }

        return Optional.empty();
    }

    /**
     * Handle the deletion of a manufacturer
     *
     * @param editFormCommand
     * @throws Exception
     */
    public void deleteManufacturer(EditFormCommand editFormCommand) throws Exception {
        EditForm editForm = editFormCommand.getEditForm();
        if (Objects.nonNull(editForm)) {
            Manufacturer manufacturer =
                    (Manufacturer) this.manufacturerFormFactory.buildEntityFromForm(editFormCommand.getEditForm());
            log.info("Deleting manufacturer: {}", manufacturer.toString());
            this.sqlManufacturerRepository.delete(manufacturer);
        }
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
    public static SearchCommand createSearchCommand(PaginationDTO manufacturersPaginationDTO) {
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
