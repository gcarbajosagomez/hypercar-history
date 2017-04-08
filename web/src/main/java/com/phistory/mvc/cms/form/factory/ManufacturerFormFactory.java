
package com.phistory.mvc.cms.form.factory;

import java.sql.Blob;
import java.util.Optional;

import javax.inject.Inject;

import com.phistory.mvc.cms.command.PictureEditCommand;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phistory.mvc.cms.form.ManufacturerForm;
import com.phistory.data.dao.sql.SqlPictureDAO;
import com.phistory.data.model.Manufacturer;
import com.phistory.data.model.picture.Picture;
import com.phistory.data.model.util.PictureUtil;

/**
 * Creates new Manufacturers out of the data contained in ManufacturersForms and vice versa
 *
 * @author Gonzalo
 */
@Slf4j
@Component
public class ManufacturerFormFactory implements EntityFormFactory<Manufacturer, ManufacturerForm> {
    private SqlPictureDAO sqlPictureDAO;

    @Inject
    public ManufacturerFormFactory(SqlPictureDAO sqlPictureDAO) {
        this.sqlPictureDAO = sqlPictureDAO;
    }

    /**
     * Create a new ManufacturerForm out of the data contained in a Manufacturer
     */
    @Override
    public ManufacturerForm createFormFromEntity(Manufacturer manufacturer) {
        try {
            ManufacturerForm manufacturerForm = new ManufacturerForm(manufacturer.getId(),
                                                                     manufacturer.getName(),
                                                                     manufacturer.getNationality(),
                                                                     null,
                                                                     manufacturer.getHistoryES(),
                                                                     manufacturer.getHistoryEN());
            if (manufacturer.getId() != null) {
                try {
                    PictureEditCommand pictureEditCommand = new PictureEditCommand(new Picture(), null);
                    Optional<Picture> carPreview = Optional.of(sqlPictureDAO.getManufacturerLogo(manufacturer.getId()));

                    if (carPreview.isPresent()) {
                        pictureEditCommand.setPicture(carPreview.get());
                    }

                    manufacturerForm.setPreviewPictureEditCommand(pictureEditCommand);
                } catch (Exception e) {
                    log.error(e.toString(), e);
                }
            }

            return manufacturerForm;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new ManufacturerForm();
    }

    /**
     * Create a new Manufacturer out of the data contained in a ManufacturerForm
     *
     * @throws Exception
     */
    @Override
    public Manufacturer createEntityFromForm(ManufacturerForm manufacturerForm) {
        try {
            Optional<MultipartFile> logoFile = Optional.ofNullable(manufacturerForm.getPreviewPictureEditCommand()
                                                                                   .getPictureFile());
            Optional<Blob> logo = Optional.empty();

            if (manufacturerForm.getPreviewPictureEditCommand().getPicture() != null) {
                logo = Optional.ofNullable(manufacturerForm.getPreviewPictureEditCommand().getPicture().getImage());
            }

            if ((logoFile.isPresent() && logoFile.get().getSize() > 0) &&
                (!logo.isPresent() || (logo.isPresent() && logo.get().length() == 0))) {
                logo = Optional.of(PictureUtil.createPictureFromMultipartFile(logoFile.get(), sqlPictureDAO));
            }

            Manufacturer object = new Manufacturer(manufacturerForm.getId(),
                                                   manufacturerForm.getName(),
                                                   manufacturerForm.getNationality(),
                                                   logo.isPresent() ? logo.get() : null,
                                                   manufacturerForm.getHistoryES(),
                                                   manufacturerForm.getHistoryEN());

            return object;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new Manufacturer();
    }
}
