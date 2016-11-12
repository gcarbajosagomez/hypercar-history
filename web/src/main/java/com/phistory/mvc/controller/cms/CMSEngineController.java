package com.phistory.mvc.controller.cms;

import static com.phistory.mvc.controller.BaseControllerData.ENGINES_URL;
import static com.phistory.mvc.controller.BaseControllerData.ID;
import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.springframework.config.WebSecurityConfig.USER_ROLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.inject.Inject;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phistory.mvc.cms.command.EngineFormEditCommand;
import com.phistory.mvc.cms.form.EngineForm;
import com.phistory.mvc.cms.form.creator.EngineFormCreator;
import com.phistory.mvc.controller.cms.util.EngineControllerUtil;
import com.phistory.data.model.engine.Engine;

/**
 * @author Gonzalo
 */
@Secured(USER_ROLE)
@Slf4j
@Controller
@RequestMapping(value = {CMS_CONTEXT + ENGINES_URL, CMS_CONTEXT + ENGINES_URL + "/{" + ID + "}"})
public class CMSEngineController extends CMSBaseController {

    private static final String ENGINE_EDIT_FORM_COMMAND = "EEFC";

    @Inject
    private EngineFormCreator engineFormCreator;
    @Inject
    private EngineControllerUtil engineControllerUtil;

    @RequestMapping(method = GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EngineForm handleListEngineById(@ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command) {
        return command.getEngineForm();
    }

    @RequestMapping(value = {SAVE_URL, EDIT_URL},
                    method = {POST, PUT},
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Model handleSaveOrEditEngine(Model model,
                                        @Valid @RequestBody(required = true) EngineFormEditCommand command,
                                        BindingResult result) {
        Engine engine = this.engineFormCreator.createEntityFromForm(command.getEngineForm());

        try {
            if (!result.hasErrors()) {
                super.getEngineDAO().saveOrEdit(engine);

                String successMessage = super.getMessageSource()
                                             .getMessage(ENTITY_EDITED_SUCCESSFULLY_RESULT_MESSAGE,
                                                         new Object[]{engine.getFriendlyName()},
                                                         LocaleContextHolder.getLocale());
                model.addAttribute(SUCCESS_MESSAGE, successMessage);
                model.addAttribute(ENGINE, engine);
            }
        } catch (Exception e) {
            model.addAttribute(EXCEPTION_MESSAGE, e.toString());
        }

        model.addAttribute(ENGINE, engine);

        return model;
    }

    @RequestMapping(value = DELETE_URL,
                    method = POST)
    public void handleDeleteEngine(Model model,
                                   BindingResult result,
                                   @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND) EngineFormEditCommand command) {
        if (!result.hasErrors()) {
            try {
                this.engineControllerUtil.deleteEngine(command);
            } catch (Exception e) {
                log.error(e.toString(), e);
                model.addAttribute(EXCEPTION_MESSAGE, e.toString());
            }
        }
    }

    @ModelAttribute(value = ENGINE_EDIT_FORM_COMMAND)
    public EngineFormEditCommand createEditFormCommand(@PathVariable(ID) Long engineId) {
        EngineFormEditCommand command = new EngineFormEditCommand();

        if (engineId != null) {
            Engine engine = super.getEngineDAO().getById(engineId);
            EngineForm engineForm = this.engineFormCreator.createFormFromEntity(engine);
            command.setEngineForm(engineForm);
        }

        return command;
    }
}
