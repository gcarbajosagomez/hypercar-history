package com.phistory.test.integration.web.car;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.phistory.mvc.controller.BaseControllerData.CARS_URL;
import static com.phistory.mvc.dto.PaginationDTO.ITEMS_PER_PAGE_DEFAULT_VALUE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class CarListTest extends BaseIntegrationTest {

    private static final int INITIAL_NUMBER_OF_CARS_PER_PAGE_OPTIONS = 5;
    private static final int IRRELEVANT_NUMBER_OF_CARS_PER_PAGE      = 18;

    @Value("${local.server.port}")
    private int         port;
    private CarListPage carListPage;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.carListPage = new CarListPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST + this.port + "/" + IRRELEVANT_MANUFACTURER.getShortName() + "/" + CARS_URL;
    }

    @Test
    public void test_car_list_is_displayed() throws Exception {
        assertThat("Main car list div should be displayed", this.carListPage.isMainCarListDivDisplayed());
    }

    @Test
    public void test_there_are_cars_listed() throws Exception {
        assertThat("There should be at least one car listed", this.carListPage.getFirstCarListedDivId(), is(notNullValue()));
    }

    @Test
    public void test_cars_per_page_drop_down_is_displayed() {
        assertThat("Cars per page drop down should be displayed", this.carListPage.isCarsPerPageDropDownDisplayed());
    }

    @Test(dependsOnMethods = "test_cars_per_page_drop_down_is_displayed")
    public void test_cars_per_page_drop_down_value_changes_number_of_cars_listed() throws InterruptedException {
        assertThat("There should be " + ITEMS_PER_PAGE_DEFAULT_VALUE + " cars listed",
                   this.carListPage.getNumberOfCarsDisplayed(),
                   is(ITEMS_PER_PAGE_DEFAULT_VALUE));

        this.carListPage.clickCarsPerPageDropDownButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("There should be number of cars to list options displayed",
                   this.carListPage.getNumberOfCarsPerPageOptionsDisplayed(),
                   is(INITIAL_NUMBER_OF_CARS_PER_PAGE_OPTIONS));

        this.carListPage.clickNumberOfCarsPerPageOptionByValue(String.valueOf(IRRELEVANT_NUMBER_OF_CARS_PER_PAGE));
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("There should be " + IRRELEVANT_NUMBER_OF_CARS_PER_PAGE + " cars listed",
                   this.carListPage.getNumberOfCarsDisplayed(),
                   is(IRRELEVANT_NUMBER_OF_CARS_PER_PAGE));
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
