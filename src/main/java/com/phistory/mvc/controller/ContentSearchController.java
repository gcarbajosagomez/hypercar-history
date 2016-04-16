package com.phistory.mvc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.model.dto.ContentSearchDto;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.tcp.data.command.SearchCommand;
import com.tcp.data.model.car.Car;
import com.tcp.data.query.command.SimpleDataConditionCommand;
import com.tcp.data.query.command.SimpleDataConditionCommand.EntityConditionType;

import static com.phistory.mvc.controller.BaseControllerData.*;

/**
 * Controller to handle ContentSearch URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = MODELS_SEARCH_URL)
public class ContentSearchController extends BaseController implements InitializingBean
{
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	
	@RequestMapping(method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView handleModelsSearch(Model model, 
										   @RequestBody(required = true) ContentSearchDto contentSearchDto)
	{	
		try
		{
			SearchCommand searchCommand = createSearchCommand(contentSearchDto);		
		
			com.tcp.data.dto.ContentSearchDto dataContentSearchDto = getContentSearchDao().hibernateSearchSearchContent(searchCommand);
		
			model.addAttribute(CARS, dataContentSearchDto.getResults());
			model.addAttribute(MODELS, getCarDao().getDistinctModelsWithId());
			model.addAttribute(CARS_PER_PAGE_DATA, contentSearchDto.getCarsPerPage());
			model.addAttribute(PAG_NUM_DATA, contentSearchDto.getPagNum());	
			model.addAttribute(SEARCH_TOTAL_RESULTS_DATA, dataContentSearchDto.getTotalResults());
			carModelFiller.fillModel(model);
			pictureModelFiller.fillModel(model);
		
			return new ModelAndView(CARS);
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
			
			return new ModelAndView(ERROR_VIEW_NAME);
		}	
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleModelsSearch(@RequestParam(defaultValue = "0", value = PAG_NUM, required = true) int pagNum,
			  							   @RequestParam(defaultValue = "8", value = CARS_PER_PAGE, required = true) int carsPerPage,
			  							   HttpServletResponse httpServletResponse)
	{		
		try
		{
			httpServletResponse.sendRedirect(CARS_URL + "?" + PAG_NUM + "=" + pagNum + "&" + CARS_PER_PAGE + "=" + carsPerPage);
			
			return null;
		}
		catch (IOException ioe)
		{
			log.error(ioe.toString(), ioe);
			
			return new ModelAndView(ERROR_VIEW_NAME);
		}
	}
	
	/**
	 * Create a search command to search for content
	 * 
	 * @param contentSearchDto
	 * @return
	 */
	private SearchCommand createSearchCommand(ContentSearchDto contentSearchDto)
	{	
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("productionStartDate", Boolean.TRUE);		
		
		SimpleDataConditionCommand simpleDataConditionCommand = new SimpleDataConditionCommand(EntityConditionType.LIKE,
																							   new Object[]{contentSearchDto.getContentToSearch()});
		
		Map<String, SimpleDataConditionCommand> dataConditionMap = new HashMap<>();
		dataConditionMap.put("model", simpleDataConditionCommand);
		
		contentSearchDto.calculatePageFirstResult(contentSearchDto.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 dataConditionMap,
								 null,
								 orderByMap,
								 contentSearchDto.getFirstResult(),
								 contentSearchDto.getCarsPerPage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		getContentSearchDao().hibernateSearchIndexPreviouslyStoredDatabaseRecords();		
	}
}
