package com.phistory.mvc.controller.cms;

import com.phistory.data.dao.sql.impl.SQLCarInternetContentDAO;
import com.phistory.data.model.car.CarInternetContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CMSBaseController.CAR_INTERNET_CONTENTS_URL;
import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

/**
 * Controller to handle requests to "{@value CAR_INTERNET_CONTENTS_URL}" URLs
 *
 * Created by gonzalo on 9/9/16.
 */
@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = CMS_CONTEXT + CAR_INTERNET_CONTENTS_URL + "/{" + ID + "}")
public class CMSCarInternetContentEditController extends CMSBaseController {

    @Autowired
    private SQLCarInternetContentDAO carInternetContentDAO;

    @RequestMapping(value = DELETE_URL,
                    method = DELETE)
    @ResponseBody
    public String handleDeleteInternetContent(@ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND) CarInternetContent carInternetContent)
    {
        try {
            this.carInternetContentDAO.delete(carInternetContent);

            String successMessage = super.getMessageSource()
                                         .getMessage(ENTITY_DELETED_SUCCESSFULLY_RESULT_MESSAGE,
                                                     new Object[]{"Car internet content"},
                                                     LocaleContextHolder.getLocale());

            return SUCCESS_MESSAGE + " : " + successMessage;
        }
        catch (Exception e) {
            log.error("There was an error while deleting picture; %s ", carInternetContent.getId(), e);
            return EXCEPTION_MESSAGE + " : " + e.toString();
        }
    }

    @ModelAttribute(value = CAR_INTERNET_CONTENT_EDIT_FORM_COMMAND)
    public CarInternetContent createCarInternetContentCommand(@PathVariable(ID) Long caInternetContentId) throws Exception
    {
        return super.getCarInternetContentDAO().getById(caInternetContentId);
    }
}
