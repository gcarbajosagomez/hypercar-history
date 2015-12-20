package com.phistory.mvc.controller.cms;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.cms.command.CarFormEditCommand;
import com.phistory.mvc.cms.form.CarForm;
import com.phistory.mvc.cms.form.creator.CarFormCreator;
import com.phistory.mvc.cms.springframework.view.CarEditModelFIller;
import com.phistory.mvc.controller.cms.util.CarControllerUtil;
import com.phistory.mvc.model.dto.CarsPaginationDto;
import com.phistory.mvc.springframework.view.CarsListModelFiller;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.model.car.Car;

/**
 *
 * @author Gonzalo
 */
@Controller
@Slf4j
@RequestMapping(value = "/cms/cars")
public class CmsCarController extends CmsBaseController
{
    private static final String CAR_EDIT_FORM_COMMAND = "CEFC"; 
    @Inject
    private CarControllerUtil carControllerUtil;
    @Inject
    private CarFormCreator carFormCreator;
    @Inject
	private ModelFiller carModelFiller;
	@Inject
	private CarsListModelFiller carsListModelFiller;
	@Inject
	private CarEditModelFIller carEditModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleCarList(Model model,
					  				  @RequestParam(defaultValue = "1", value = PAG_NUM, required = true) int pagNum,
					  				  @RequestParam(defaultValue = "8", value = CARS_PER_PAGE, required = true) int carsPerPage)
    {		
    	try
    	{		
    		CarsPaginationDto carsPaginationDto = new CarsPaginationDto(pagNum, carsPerPage);

    		carsListModelFiller.fillPaginatedModel(model, carsPaginationDto);
			carModelFiller.fillModel(model);
			pictureModelFiller.fillModel(model);

    		return new ModelAndView();
    	}
    	catch(Exception e)
    	{
    		log.error(e.toString(), e);

    		return new ModelAndView(ERROR);
    	}		
	}
    
    @RequestMapping(method = RequestMethod.POST,
    			    consumes = MediaType.APPLICATION_JSON,
    			    produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> handlePagination(@RequestBody(required = true) CarsPaginationDto carsPaginationDto)
    {			
    	return carControllerUtil.createPaginationData(carsPaginationDto);
    }

    @RequestMapping(value = EDIT_URL,
				    method = RequestMethod.GET)
    public ModelAndView handleEditDefault(Model model,
    									  @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command)
    {
    	try
    	{
    		carModelFiller.fillModel(model);
    		carEditModelFiller.fillCarEditModel(model, command);
    		pictureModelFiller.fillModel(model);
    	}
        catch (Exception ex)
        {
        	log.error(ex.toString(), ex);
        	model.addAttribute("exceptionMessage", ex.toString());
        }

        return new ModelAndView(CAR_EDIT_VIEW_NAME);
    }

    @RequestMapping(value = EDIT_URL,
    				method = RequestMethod.POST)
    public ModelAndView handleSaveOrEditCar(HttpServletRequest request,
    										Model model,
    										@Valid @ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command,
    										BindingResult result)
    {
    	try
    	{
    		if (!result.hasErrors())
    		{        	
        		Car car = carControllerUtil.saveOrEditCar(command);  
        		
        		String successMessage = getMessageSource().getMessage("entitySavedSuccessfully",
						  											  new Object[]{car.getFriendlyName()},
						  											  Locale.getDefault());     
        		model.addAttribute("successMessage", successMessage);
        	}
    		else
    		{
    			String errorMessage = getMessageSource().getMessage("entityContainedErrors",null, Locale.getDefault());     
    			model.addAttribute("exceptionMessage", errorMessage);
    		}
    	}
        catch (Exception e)
        {
        	log.error(e.toString(), e);
        	model.addAttribute("exceptionMessage", e.toString());
        	
        }
		finally
		{
			carModelFiller.fillModel(model);
			carEditModelFiller.fillCarEditModel(model, command);
			pictureModelFiller.fillModel(model);
		}
        
        return new ModelAndView(CAR_EDIT_VIEW_NAME);
	}

	@RequestMapping(value = DELETE_URL,
					method = RequestMethod.POST)
	public ModelAndView handleDeleteCar(HttpServletResponse response,
										Model model,
										BindingResult result,
										@ModelAttribute(value = CAR_EDIT_FORM_COMMAND) CarFormEditCommand command)
	{
		if (!result.hasErrors())
		{
			try		
			{
				carControllerUtil.deleteCar(command);
				response.sendRedirect(EDIT_URL);
			}
			catch (Exception e)
			{
				log.error(e.toString(), e);
				model.addAttribute("exceptionMessage", e.toString());
				carModelFiller.fillModel(model);
				
				return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
			}
		}
		
		return new ModelAndView(MANUFACTURER_EDIT_VIEW_NAME);
	}

    @ModelAttribute(value = CAR_EDIT_FORM_COMMAND)
    public CarFormEditCommand createCarEditFormCommand(@RequestParam(value = CAR_ID, required = false) Long carId)
    {
        CarFormEditCommand command = new CarFormEditCommand();

        if (carId != null)
        {
            Car car = getCarDao().getById(carId);
            CarForm carForm = carFormCreator.createFormFromEntity(car);
            
            command.setCarForm(carForm);
        }
        
        return command;
    } 
}
