package com.phistory.data.dao.sql.impl;

import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phistory.data.command.PictureDataCommand;
import com.phistory.data.dao.DAO;
import com.phistory.data.model.Picture;
import com.phistory.data.model.Picture.PictureType;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class PictureDAO extends DAO<Picture, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
	@Override
    public List<Picture> getAll()
    {
        return super.getCurrentSession().createQuery("FROM Picture").list();
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public Picture getById(Long id)
    {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.id = :id");
        q.setParameter("id", id);
        Picture picture = (Picture) q.uniqueResult();
               
        return picture;        
    }

	public List<Long> getIdsByCarId(Long carId)
    {
        StringBuilder stringBuilder = new StringBuilder("SELECT picture.id"
                                                     + " FROM Picture AS picture"
                                                     + " WHERE picture.type = " + PictureType.PICTURE.ordinal());
        
        if (carId != null)
        {
        	stringBuilder.append(" AND picture.car.id = :carId"
        					   + " ORDER BY picture.id ASC");
        }
       
        Query q = getCurrentSession().createQuery(stringBuilder.toString());
            
        if (carId != null)
        {
            q.setParameter("carId", carId);
        }

        return q.list();
    }

    public Picture getCarPreview(Long carId)
    {
        Query q = getCurrentSession().createQuery("FROM Picture AS picture"
                                               + " WHERE picture.car.id = :carId"
                                               + " AND picture.type = " + PictureType.PREVIEW_PICTURE.ordinal());

        q.setParameter("carId", carId);
        Picture picture = (Picture) q.uniqueResult();
                  
        return picture;        
    }

    public Picture getManufacturerLogo(Long manufacturerId)
    {
        Picture picture = new Picture();

        
        Query q = getCurrentSession().createQuery("SELECT manufacturer.logo"
                                               + " FROM Manufacturer AS manufacturer"
                                               + " WHERE manufacturer.id = :manufacturerId");

        q.setParameter("manufacturerId", manufacturerId);
        picture.setImage((Blob) q.uniqueResult());

        return picture;        
    }

    public void saveOrEdit(PictureDataCommand pictureEditCommand) throws IOException
    {        
        LobCreator lobCreator = Hibernate.getLobCreator(getCurrentSession());
        Blob pictureBlob = lobCreator.createBlob(pictureEditCommand.getMultipartFile().getInputStream(), -1);
            
        Picture picture = pictureEditCommand.getPicture();
        picture.setImage(pictureBlob);            
       
        saveOrEdit(picture);
    }
    
    public Long count()
    {       
        return (Long) super.getCurrentSession()
                           .createQuery("SELECT COUNT (picture.car.id) FROM Picture AS picture")
                           .uniqueResult();
    }

    public List<Picture> getPaginated(int firstResult, int limit)
    {
        Query query = super.getCurrentSession()
                           .createQuery("FROM Picture AS picture"
                                     + " ORDER BY picture.id");

        query.setFirstResult(firstResult);
        query.setMaxResults(limit);

        return query.list();
    }
}