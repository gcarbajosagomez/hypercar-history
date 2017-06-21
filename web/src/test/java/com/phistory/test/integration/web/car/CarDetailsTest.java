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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class CarDetailsTest extends BaseIntegrationTest {

    private static final String IRRELEVANT_CAR_MODEL = "zonda-c12";

    @Value("${local.server.port}")
    private int            port;
    private CarDetailsPage carDetailsPage;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.carDetailsPage = new CarDetailsPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               CARS_URL + "/" +
               IRRELEVANT_CAR_MODEL;
    }

    @Test
    public void test_is_car_pictures_carousel_is_displayed() {
        assertThat("Car pictures carousel should be displayed", this.carDetailsPage.isCarPicturesCarouselDisplayed(), is(true));
    }

    @Test(dependsOnMethods = "test_is_car_pictures_carousel_is_displayed")
    public void test_is_car_pictures_carousel_has_images() {
        assertThat("Car pictures carousel should have images", this.carDetailsPage.carPicturesCarouselHasImages(), is(true));
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
