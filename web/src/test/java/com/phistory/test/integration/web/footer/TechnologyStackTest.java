package com.phistory.test.integration.web.footer;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class TechnologyStackTest extends BaseIntegrationTest {

    @Value("${local.server.port}")
    private int                 port;
    private TechnologyStackPage technologyStackPage;
    private FooterPage          footerPage;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.technologyStackPage = new TechnologyStackPage(this.webDriver);
        this.footerPage = new FooterPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/";
    }

    @Test
    public void test_click_technology_stack_link_should_display_technology_stack_modal() throws InterruptedException {
        assertThat("Technology Stack modal should not be displayed at this point",
                   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
                   is(false));
        this.assert_click_technology_stack_link_should_display_technology_stack_modal();
    }

    @Test(dependsOnMethods = "test_click_technology_stack_link_should_display_technology_stack_modal")
    public void test_click_dismiss_technology_stack_should_dismiss_technology_stack_modal() throws InterruptedException {
        assertThat("Technology Stack modal should not be displayed at this point",
                   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
                   is(false));
        this.assert_click_technology_stack_link_should_display_technology_stack_modal();
        this.technologyStackPage.clickDismissTechnologyStackModalButton();
        Thread.sleep(1000);
        assertThat("Technology Stack modal should not be displayed at this point",
                   this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
                   is(false));
    }

    private void assert_click_technology_stack_link_should_display_technology_stack_modal() throws InterruptedException {
        this.footerPage.clickTechnologyStackLink();
        Thread.sleep(1000);
        assertThat("Technology Stack modal should be displayed", this.technologyStackPage.isTechnologyStackModalDivDisplayed(),
                   is(true));
    }

    @AfterMethod
    public void dismissContactUsModal() throws InterruptedException {
        if (this.technologyStackPage.isTechnologyStackModalDivDisplayed()) {
            this.technologyStackPage.clickDismissTechnologyStackModalButton();
            Thread.sleep(1000);
        }
    }

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
