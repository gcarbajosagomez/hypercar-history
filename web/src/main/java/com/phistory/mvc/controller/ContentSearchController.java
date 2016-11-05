package com.phistory.mvc.controller;

import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.car.Car;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import com.phistory.data.query.command.SimpleDataConditionCommand.EntityConditionType;
import com.phistory.mvc.model.dto.ContentSearchDto;
import com.phistory.mvc.springframework.view.ModelFiller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.phistory.mvc.controller.BaseControllerData.MODELS_SEARCH_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller to handle ContentSearch URLs
 * 
 * @author Gonzalo
 *
 */
@Slf4j
@Controller
@RequestMapping(value = MODELS_SEARCH_URL,
				method = HEAD)
public class ContentSearchController extends BaseController implements InitializingBean
{
	@Inject
	private ModelFiller carModelFiller;
	@Inject
	private ModelFiller pictureModelFiller;
	
	@RequestMapping(method = GET)
	@ResponseBody
	public ModelAndView handleModelsSearch(Model model, 
										   ContentSearchDto contentSearchDto)
	{	
		try
		{
            ContentSearchDto clonedContentSearchDto = contentSearchDto.clone();
            clonedContentSearchDto.setCarsPerPage(0);
			SearchCommand searchCommand = this.createSearchCommand(clonedContentSearchDto);
			com.phistory.data.dto.ContentSearchDto dataContentSearchDto = this.getContentSearchDAO().hibernateSearchSearchContent(searchCommand);
            List<Object> searchResults = dataContentSearchDto.getResults();

            model.addAttribute(CARS, this.extractModelsListFromSearchResults(searchResults, contentSearchDto));
            model.addAttribute(MODELS, searchResults);
			model.addAttribute(CARS_PER_PAGE_DATA, contentSearchDto.getCarsPerPage());
			model.addAttribute(PAG_NUM_DATA, contentSearchDto.getPagNum());	
			model.addAttribute(SEARCH_TOTAL_RESULTS_DATA, dataContentSearchDto.getTotalResults());
			model.addAttribute(CONTENT_TO_SEARCH_DATA, contentSearchDto.getContentToSearch());
			this.carModelFiller.fillModel(model);
			this.pictureModelFiller.fillModel(model);
		
			return new ModelAndView(CARS);
		}
		catch(Exception e)
		{
			log.error(e.toString(), e);
			
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
		orderByMap.put(Car.PRODUCTION_START_DATE_PROPERTY_NAME, Boolean.TRUE);
		orderByMap.put(Car.MODEL_PROPERTY_NAME, Boolean.TRUE);

		SimpleDataConditionCommand simpleDataConditionCommand = new SimpleDataConditionCommand(EntityConditionType.LIKE,
																							   new Object[]{contentSearchDto.getContentToSearch()});
		
		Map<String, SimpleDataConditionCommand> dataConditionMap = new HashMap<>();
		dataConditionMap.put(Car.MODEL_PROPERTY_NAME, simpleDataConditionCommand);
        int paginationFirstResult = contentSearchDto.calculatePageFirstResult(contentSearchDto.getCarsPerPage());
		
		return new SearchCommand(Car.class,
								 null,
								 dataConditionMap,
								 null,
								 orderByMap,
								 Collections.EMPTY_LIST,
                                 paginationFirstResult,
								 contentSearchDto.getCarsPerPage());
	}

    private List<Object> extractModelsListFromSearchResults(List<Object> searchResults, ContentSearchDto contentSearchDto) {
        int fromIndex = contentSearchDto.calculatePageFirstResult(contentSearchDto.getCarsPerPage());
        int toIndex = fromIndex + contentSearchDto.getCarsPerPage();

        if (toIndex > searchResults.size()) {
            toIndex = searchResults.size();
        }
        return searchResults.subList(fromIndex, toIndex);
    }
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.getContentSearchDAO().hibernateSearchIndexPreviouslyStoredDatabaseRecords();
	}
}
