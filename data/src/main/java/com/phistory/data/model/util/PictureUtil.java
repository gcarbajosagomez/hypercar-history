package com.phistory.data.model.util;

import java.sql.Blob;
import java.util.List;
import java.util.Random;

import com.phistory.data.dao.SQLDAO;
import com.phistory.data.model.picture.Picture;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Hibernate;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Gonzalo
 */
@Slf4j
public class PictureUtil
{
    public static Blob createPictureFromMultipartFile(MultipartFile file, SQLDAO<?, Long> SQLDAO)
    {
        Blob picture = null;
        try
        {
            LobCreator lobCreator = Hibernate.getLobCreator(SQLDAO.openSession());
            picture = lobCreator.createBlob(file.getInputStream(), -1);
        } 
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return picture;
    }

    public static Picture getPreviewPictureFromCandidates(List<Picture> previewCandidates) {
        if (!previewCandidates.isEmpty()) {
            return previewCandidates.get(new Random().nextInt(previewCandidates.size()));
        }
        return null;
    }
}
