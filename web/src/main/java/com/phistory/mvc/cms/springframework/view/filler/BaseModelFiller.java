package com.phistory.mvc.cms.springframework.view.filler;

import com.phistory.data.dao.sql.SqlCarDAO;
import com.phistory.mvc.springframework.view.filler.ModelFiller;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.inject.Inject;

import static com.phistory.mvc.cms.command.EntityManagementQueryType.RELOAD_CARS;
import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_MODELS;
import static com.phistory.mvc.cms.controller.CMSBaseController.EDIT_URL;

/**
 * Fills a Spring Framework {@link Model} with the basic information for the CMS web context
 * <p>
 * Created by Gonzalo Carbajosa on 3/12/16.
 */
@Component("cmsBaseModelFiller")
public class BaseModelFiller implements ModelFiller {
    private SqlCarDAO sqlCarDAO;

    @Inject
    public BaseModelFiller(SqlCarDAO sqlCarDAO) {
        this.sqlCarDAO = sqlCarDAO;
    }

    @Override
    public Model fillModel(Model model) {
        model.addAttribute("editURL", EDIT_URL);
        model.addAttribute("reloadCarsEntityManagementAction", RELOAD_CARS);
        model.addAttribute(CMS_MODELS, this.sqlCarDAO.getAllOrderedByProductionStartDate());
        return model;
    }
}
