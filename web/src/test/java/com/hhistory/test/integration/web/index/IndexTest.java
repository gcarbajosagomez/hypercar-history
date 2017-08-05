package com.hhistory.test.integration.web.index;

import com.hhistory.Main;
import com.hhistory.test.integration.web.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.hhistory.mvc.controller.BaseControllerData.INDEX_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class IndexTest extends BaseIntegrationTest {

    private IndexPage indexPage;
    @Value("${local.server.port}")
    private int       port;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.indexPage = new IndexPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               INDEX_URL;
    }

    @Test
    public void test_jumbotron_is_displayed() {
        assertThat("Jumbotron should be displayed", this.indexPage.isJumbotronDisplayed(), is(true));
    }

    @Test
    public void test_carousel_is_displayed() {
        assertThat("Carousel should be displayed", this.indexPage.isCarouselDisplayed(), is(true));
    }

    @Test(dependsOnMethods = "test_carousel_is_displayed")
    public void when_carousel_is_displayed_test_carousel_has_images() {
        assertThat("Carousel should have valid images", this.indexPage.carouselHasValidImages(), is(true));
    }

    @Test(dependsOnMethods = "when_carousel_is_displayed_test_carousel_has_images")
    public void test_carousel_has_an_active_image() {
        assertThat("Carousel should have an active image", this.indexPage.carouselHasAnActiveImage(), is(true));
    }

    @Test(dependsOnMethods = "test_carousel_has_an_active_image")
    public void when_carousel_has_images_click_left_image_should_slide() throws InterruptedException {
        String previousCarouselActiveImageDivId = this.indexPage.getCarouselActiveImageDivId();
        this.indexPage.clickLeftCarouselControl();
        Thread.sleep(1500);
        assertThat("Carousel should image should have slid after clicking left arrow",
                   this.indexPage.getCarouselActiveImageDivId(),
                   is(not(previousCarouselActiveImageDivId)));
    }

    @Test(dependsOnMethods = "test_carousel_has_an_active_image")
    public void when_carousel_has_images_click_right_image_should_slide() throws InterruptedException {
        String previousCarouselActiveImageDivId = this.indexPage.getCarouselActiveImageDivId();
        this.indexPage.clickRightCarouselControl();
        Thread.sleep(1500);
        assertThat("Carousel should image should have slid after clicking right arrow",
                   this.indexPage.getCarouselActiveImageDivId(),
                   is(not(previousCarouselActiveImageDivId)));
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
