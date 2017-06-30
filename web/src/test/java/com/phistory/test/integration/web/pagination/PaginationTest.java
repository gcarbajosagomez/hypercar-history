package com.phistory.test.integration.web.pagination;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import com.phistory.test.integration.web.car.CarListPage;
import com.phistory.test.integration.web.navbar.NavBarPage;
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
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class PaginationTest extends BaseIntegrationTest {

    private static final String IRRELEVANT_CONTENT_TO_SEARCH = "zonda";

    private CarListPage carListPage;
    private NavBarPage  navBarPage;
    @Value("${local.server.port}")
    private int         port;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.carListPage = new CarListPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               CARS_URL;
    }

    @Test
    public void test_pagination_is_displayed() throws Exception {
        assertThat("Pagination should be displayed", this.carListPage.isPaginationDivDisplayed());
        assertThat("Paginator should be displayed", this.carListPage.isPaginatorDisplayed());
        assertThat("Paginator should contain at least one page", this.carListPage.paginatorHasPages());
    }

    @Test(dependsOnMethods = "test_pagination_is_displayed")
    public void test_paginate_forward() throws Exception {
        String initialFirstCarListedDivId = this.carListPage.getFirstCarListedDivId();

        this.carListPage.paginate(2);
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("After paginating cars listed should have changed", this.carListPage.getFirstCarListedDivId(),
                   is(not(initialFirstCarListedDivId)));
    }

    @Test(dependsOnMethods = "test_paginate_forward")
    public void test_paginate_backward() throws Exception {
        String initialFirstCarListedDivId = this.carListPage.getFirstCarListedDivId();

        this.carListPage.paginate(1);
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("After paginating cars listed should have changed", this.carListPage.getFirstCarListedDivId(),
                   is(not(initialFirstCarListedDivId)));
    }

    @Test(dependsOnMethods = "test_pagination_is_displayed")
    public void test_after_language_change_pagination_is_displayed() throws Exception {
        this.navBarPage = new NavBarPage(this.webDriver);
        this.navBarPage.initializePageElements();
        assertThat("Main navBar should be displayed", this.navBarPage.isMainNavBarDisplayed());
        assertThat("Spanish language link should not be displayed at this point",
                   this.navBarPage.isSpanishLanguageLinkDisplayed(), is(false));
        assertThat("English language link should not be displayed at this point",
                   this.navBarPage.isEnglishLanguageLinkDisplayed(), is(false));

        this.navBarPage.clickLanguageDropDownToggle();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS / 10);
        assertThat("Spanish language link should be displayed at this point", this.navBarPage.isSpanishLanguageLinkDisplayed());
        assertThat("English language link should be displayed at this point", this.navBarPage.isEnglishLanguageLinkDisplayed());

        this.navBarPage.clickEnglishLanguageLink();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.navBarPage.clickLanguageDropDownToggle();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS / 10);
        this.navBarPage.clickSpanishLanguageLink();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.test_pagination_is_displayed();
    }

    @Test(dependsOnMethods = "test_pagination_is_displayed")
    public void test_after_content_search_pagination_is_displayed() throws Exception {
        this.navBarPage = new NavBarPage(this.webDriver);
        this.navBarPage.initializePageElements();
        assertThat("Main navBar should be displayed", this.navBarPage.isMainNavBarDisplayed());
        assertThat("Content search icon should be displayed at this point", this.navBarPage.isContentSearchIconDisplayed());
        assertThat("Content search input should be displayed at this point", this.navBarPage.isContentSearchIconDisplayed());

        this.navBarPage.clickContentSearchIcon();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS / 2);
        this.navBarPage.typeContentToSearch(IRRELEVANT_CONTENT_TO_SEARCH);
        this.navBarPage.performSearchContent();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.test_pagination_is_displayed();
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
