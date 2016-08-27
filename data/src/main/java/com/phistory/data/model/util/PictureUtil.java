package com.phistory.data.model.util;

import java.sql.Blob;

import com.phistory.data.dao.generic.Dao;
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
    public static Blob createPictureFromMultipartFile(MultipartFile file, Dao<?, Long> dao)
    {
        Blob picture = null;
        try
        {
            LobCreator lobCreator = Hibernate.getLobCreator(dao.openSession());
            picture = lobCreator.createBlob(file.getInputStream(), -1);
        } 
        catch (Exception e)
        {
            log.error(e.toString(), e);
        }
        
        return picture;
    }
}
