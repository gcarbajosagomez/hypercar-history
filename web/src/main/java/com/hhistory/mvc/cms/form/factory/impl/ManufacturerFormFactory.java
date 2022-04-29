
package com.hhistory.mvc.cms.form.factory.impl;

import com.hhistory.data.dao.sql.SqlPictureDAO;
import com.hhistory.data.model.Manufacturer;
import com.hhistory.data.model.picture.Picture;
import com.hhistory.data.model.util.PictureUtil;
import com.hhistory.mvc.cms.command.PictureEditCommand;
import com.hhistory.mvc.cms.form.ManufacturerEditForm;
import com.hhistory.mvc.cms.form.factory.EntityFormFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.sql.Blob;
import java.util.Objects;
import java.util.Optional;

/**
 * Creates new Manufacturers out of the data contained in ManufacturersForms and vice versa
 *
 * @author Gonzalo
 */
@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
@Component
public class ManufacturerFormFactory implements EntityFormFactory<Manufacturer, ManufacturerEditForm> {

    private SqlPictureDAO sqlPictureDAO;
    private PictureUtil   pictureUtil;

    @Override
    public ManufacturerEditForm buildFormFromEntity(Manufacturer manufacturer) {
        try {
            ManufacturerEditForm manufacturerEditForm = new ManufacturerEditForm(manufacturer.getId(),
                                                                                 manufacturer.getName(),
                                                                                 manufacturer.getNationality(),
                                                                                 null,
                                                                                 manufacturer.getHistoryES(),
                                                                                 manufacturer.getHistoryEN());

            Long manufacturerId = manufacturer.getId();
            if (Objects.nonNull(manufacturerId)) {
                try {
                    PictureEditCommand pictureEditCommand = new PictureEditCommand(new Picture(), null);
                    Optional<Picture> carPreview = Optional.of(this.sqlPictureDAO.getManufacturerLogo(manufacturerId));

                    carPreview.ifPresent(pictureEditCommand::setPicture);

                    manufacturerEditForm.setPreviewPictureEditCommand(pictureEditCommand);
                } catch (Exception e) {
                    log.error(e.toString(), e);
                }
            }

            return manufacturerEditForm;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new ManufacturerEditForm();
    }

    @Override
    public Manufacturer buildEntityFromForm(ManufacturerEditForm manufacturerEditForm) {
        try {
            Optional<MultipartFile> logoFile = Optional.ofNullable(manufacturerEditForm.getPreviewPictureEditCommand()
                                                                                       .getPictureFile());
            Optional<Blob> logo = Optional.empty();

            if (manufacturerEditForm.getPreviewPictureEditCommand().getPicture() != null) {
                logo = Optional.ofNullable(manufacturerEditForm.getPreviewPictureEditCommand().getPicture().getImage());
            }

            if ((logoFile.isPresent() && logoFile.get().getSize() > 0) &&
                (logo.isEmpty() || logo.get().length() == 0)) {
                logo = Optional.of(this.pictureUtil.createPictureFromMultipartFile(logoFile.get(),
                                                                                   this.sqlPictureDAO));
            }

            return new Manufacturer(manufacturerEditForm.getId(),
                                    manufacturerEditForm.getName(),
                                    manufacturerEditForm.getNationality(),
                                    logo.orElse(null),
                                    manufacturerEditForm.getHistoryES(),
                                    manufacturerEditForm.getHistoryEN());
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return new Manufacturer();
    }
}
