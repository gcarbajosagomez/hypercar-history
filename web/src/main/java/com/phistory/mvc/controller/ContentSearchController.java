package com.phistory.mvc.controller;

import static com.phistory.mvc.controller.BaseControllerData.MODELS_SEARCH_URL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.phistory.mvc.model.dto.ContentSearchDto;
import com.phistory.mvc.springframework.view.ModelFiller;
import com.phistory.data.command.SearchCommand;
import com.phistory.data.model.car.Car;
import com.phistory.data.query.command.SimpleDataConditionCommand;
import com.phistory.data.query.command.SimpleDataConditionCommand.EntityConditionType;

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
			SearchCommand searchCommand = this.createSearchCommand(clonedContentSearchDto, Arrays.asList(Car.MODEL_PROPERTY_NAME));
			com.phistory.data.dto.ContentSearchDto dataContentSearchDto = this.getContentSearchDao().hibernateSearchSearchContent(searchCommand);
            List<Car> searchResults = dataContentSearchDto.getResults()
                                                              .stream()
                                                              .map(result ->  {
                                                                    String modelName = String.valueOf(((Object[]) result)[0]);
                                                                    return super.getCarDao().getByModelName(modelName);
                                                              })
                                                              .collect(Collectors.toList());

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
	private SearchCommand createSearchCommand(ContentSearchDto contentSearchDto, List<String> projectedFields)
	{	
		Map<String, Boolean> orderByMap = new HashMap<>();
		orderByMap.put("productionStartDate", Boolean.TRUE);		
		
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
								 projectedFields,
                                 paginationFirstResult,
								 contentSearchDto.getCarsPerPage());
	}

    private List<Car> extractModelsListFromSearchResults(List<Car> searchResults, ContentSearchDto contentSearchDto) {
        int fromIndex = contentSearchDto.calculatePageFirstResult(contentSearchDto.getCarsPerPage());
        int toIndex = contentSearchDto.getCarsPerPage();

        if (toIndex > searchResults.size()) {
            toIndex = searchResults.size();
        }
        toIndex = fromIndex + toIndex;
        return searchResults.subList(fromIndex, toIndex);
    }
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.getContentSearchDao().hibernateSearchIndexPreviouslyStoredDatabaseRecords();
	}
}
