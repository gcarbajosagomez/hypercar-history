package com.tcp.data.dao.impl;

import com.tcp.data.dao.generic.Dao;
import com.tcp.data.model.Visit;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gonzalo
 */
@Transactional
@Repository
public class VisitDao extends Dao<Visit, Long>
{
	/**
	 * {@inheritDoc}
	 */
    @Override
    public List<Visit> getAll()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
   	 * {@inheritDoc}
   	 */
    @Override
    public Visit getById(Long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
