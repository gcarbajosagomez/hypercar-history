package com.phistory.mvc.cms.controller;

import com.phistory.mvc.cms.command.EngineEditFormCommand;
import com.phistory.mvc.cms.controller.util.CMSEngineControllerUtil;
import com.phistory.mvc.cms.dto.CrudOperationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.validation.Valid;

import static com.phistory.mvc.cms.controller.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.BaseControllerData.ENGINE_URL;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Gonzalo Carbajosa on 14/04/17.
 */
@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = CMS_CONTEXT + ENGINE_URL)
public class CMSEngineController extends CMSBaseController {

    private CMSEngineControllerUtil cmsEngineControllerUtil;

    @Inject
    public CMSEngineController(CMSEngineControllerUtil cmsEngineControllerUtil) {
        this.cmsEngineControllerUtil = cmsEngineControllerUtil;
    }

    @RequestMapping(value = SAVE_URL,
            method = POST,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CrudOperationDTO handleSaveNewEngine(@Valid @RequestBody EngineEditFormCommand command,
                                                BindingResult result) {

        return this.cmsEngineControllerUtil.saveOrEditEngine(command,
                                                             result,
                                                             ENTITY_SAVED_SUCCESSFULLY_TEXT_SOURCE_KEY);
    }
}
