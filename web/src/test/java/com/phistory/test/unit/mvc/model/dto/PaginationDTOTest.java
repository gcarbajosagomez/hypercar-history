package com.phistory.test.unit.mvc.model.dto;

import com.phistory.mvc.model.dto.PaginationDTO;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gonzalo on 10/8/16.
 */
public class PaginationDTOTest {
    private static final Integer IRRELEVANT_LOW_ITEMS_PER_PAGE = 3;
    private static final Integer IRRELEVANT_HIGH_ITEMS_PER_PAGE = 50;
    private static final Integer FIRST_PAGE_NUM = 1;
    private static final Integer IRRELEVANT_LOW_PAGE_NUM = 3;
    private static final Integer IRRELEVANT_HIGH_PAGE_NUM = 6;

    @DataProvider(name = "pageFirstResults")
    public Object[][] getPageFirstResults() {
        return new Object[][] {
                {null, 0, 0},
                {null, 1, 0},
                {IRRELEVANT_LOW_ITEMS_PER_PAGE, 0, 0},
                {IRRELEVANT_HIGH_ITEMS_PER_PAGE, 0, 0},
                {IRRELEVANT_LOW_ITEMS_PER_PAGE, FIRST_PAGE_NUM, 0},
                {IRRELEVANT_LOW_ITEMS_PER_PAGE, IRRELEVANT_HIGH_PAGE_NUM, 15},
                {IRRELEVANT_LOW_ITEMS_PER_PAGE, IRRELEVANT_LOW_PAGE_NUM, 6},
                {IRRELEVANT_HIGH_ITEMS_PER_PAGE, FIRST_PAGE_NUM, 0},
                {IRRELEVANT_HIGH_ITEMS_PER_PAGE, IRRELEVANT_HIGH_PAGE_NUM, 250},
                {IRRELEVANT_HIGH_ITEMS_PER_PAGE, IRRELEVANT_LOW_PAGE_NUM, 100}
        };
    }

    @Test(dataProvider = "pageFirstResults")
    public void testCalculatePageFirstResult(Integer itemsPerPage, Integer pagNum, Integer expectedResult) {
        PaginationDTO paginationDTO = new PaginationDTO(pagNum, itemsPerPage);
        assertThat(paginationDTO.getFirstResult(), is(expectedResult));
    }
}
