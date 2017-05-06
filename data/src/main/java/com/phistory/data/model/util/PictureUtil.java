package com.phistory.data.model.util;

import com.phistory.data.dao.sql.SqlDAO;
import com.phistory.data.model.picture.Picture;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Gonzalo
 */
@Component
@Slf4j
public class PictureUtil {

    public Blob createPictureFromMultipartFile(MultipartFile file, SqlDAO sqlDAO) {
        Blob picture = null;
        try {
            LobCreator lobCreator = Hibernate.getLobCreator(sqlDAO.getCurrentSession());
            picture = lobCreator.createBlob(file.getInputStream(), -1);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

        return picture;
    }

    public Optional<Picture> getPreviewPictureFromCandidates(List<Picture> previewCandidates) {
        if (!previewCandidates.isEmpty()) {
            int randomIndex = new Random().nextInt(previewCandidates.size());
            return Optional.of(previewCandidates.get(randomIndex));
        }
        return Optional.empty();
    }
}
