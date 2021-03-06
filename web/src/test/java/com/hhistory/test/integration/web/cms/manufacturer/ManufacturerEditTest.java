package com.hhistory.test.integration.web.cms.manufacturer;

import com.hhistory.Main;
import com.hhistory.test.integration.web.BaseIntegrationTest;
import com.hhistory.test.integration.web.cms.login.LoginPage;
import com.hhistory.test.integration.web.cms.login.LoginTestUtils;
import com.hhistory.test.integration.web.cms.modal.ConfirmModalPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.hhistory.mvc.cms.controller.CMSBaseController.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by gonzalo on 7/17/16.
 */
@SpringBootTest(
        classes = Main.class,
        webEnvironment = RANDOM_PORT)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
@Test(dependsOnGroups = {"login"})
public class ManufacturerEditTest extends BaseIntegrationTest {

    private static final String IRRELEVANT_MANUFACTURER_NAME           = "irrelevantManufacturerName";
    private static final String IRRELEVANT_NATIONALITY                 = "irrelevantNationality";
    private static final String IRRELEVANT_MANUFACTURER_HISTORY        = "irrelevantManufacturerHistory";
    private static final String IRRELEVANT_MANUFACTURER_LOGO_FILE_PATH =
            "/src/test/resources/com/hhistory/test/integration/web/static/img/irrelevant-manufacturer-logo.jpg";

    @Value("${local.server.port}")
    private int                  port;
    private ManufacturerEditPage manufacturerPage;
    private ConfirmModalPage     confirmModalPage;
    private LoginPage            loginPage;
    private LoginTestUtils       loginTestUtils;

    @BeforeClass
    @Override
    public void setupTest() throws Exception {
        super.setupBaseTest();
        this.webDriver.get(this.getTestUrl());
        this.loginPage = new LoginPage(this.webDriver);
        this.loginTestUtils = new LoginTestUtils();
        this.loginTestUtils.performCMSLogin(this.loginPage);
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + MANUFACTURERS_URL + "/" + EDIT_URL);
        this.manufacturerPage = new ManufacturerEditPage(this.webDriver);
    }

    @Override
    protected String getTestUrl() {
        return TEST_SERVER_HOST +
               this.port + "/" +
               IRRELEVANT_MANUFACTURER.getShortName() + "/" +
               CMS_CONTEXT;
    }

    @Test(groups = {"preConditions"})
    public void test_save_manufacturer_input_is_displayed() {
        assertThat("Save manufacturer button should be displayed", this.manufacturerPage.isSaveManufacturerButtonDisplayed());
    }

    @Test(groups = {"preConditions"})
    public void test_new_manufacturer_link_is_displayed() {
        assertThat("New manufacturer link should be displayed", this.manufacturerPage.isNewManufacturerLinkDisplayed());
    }

    @Test(dependsOnGroups = {"preConditions"})
    public void test_open_new_manufacturer_page() throws InterruptedException {
        this.manufacturerPage.typeName(IRRELEVANT_MANUFACTURER_NAME);
        this.manufacturerPage.typeNationality(IRRELEVANT_NATIONALITY);
        this.manufacturerPage.typeHistoryES(IRRELEVANT_MANUFACTURER_HISTORY);
        this.manufacturerPage.typeHistoryEN(IRRELEVANT_MANUFACTURER_HISTORY);
        this.manufacturerPage.selectLogoFile(PROJECT_PATH + IRRELEVANT_MANUFACTURER_LOGO_FILE_PATH);
        this.manufacturerPage.clickNewManufacturerLink();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("Successful new manufacturer alert should be displayed",
                   this.manufacturerPage.nameInputHasContent(),
                   equalTo(false));
    }

    @Test(dependsOnMethods = {"test_open_new_manufacturer_page"})
    public void test_save_new_manufacturer() throws InterruptedException {
        this.manufacturerPage.typeName(IRRELEVANT_MANUFACTURER_NAME);
        this.manufacturerPage.typeNationality(IRRELEVANT_NATIONALITY);
        this.manufacturerPage.typeHistoryES(IRRELEVANT_MANUFACTURER_HISTORY);
        this.manufacturerPage.typeHistoryEN(IRRELEVANT_MANUFACTURER_HISTORY);
        this.manufacturerPage.selectLogoFile(PROJECT_PATH + IRRELEVANT_MANUFACTURER_LOGO_FILE_PATH);
        this.manufacturerPage.clickSaveManufacturerButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.confirmModalPage = new ConfirmModalPage(this.webDriver);
        this.confirmModalPage.clickOKButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
//        assertThat("Successful new manufacturer alert should be displayed", this.manufacturerPage.isSuccessAlertDisplayed());
    }

    /*@Test(dependsOnMethods= {"test_save_new_manufacturer"})
    public void test_delete_manufacturer() throws InterruptedException
    {
        assertThat("Delete manufacturer button should be displayed", this.manufacturerPage.isDeleteManufacturerButtonDisplayed());
        this.manufacturerPage.clickDeleteManufacturerButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.confirmModalPage = new ConfirmModalPage(this.webDriver);
        this.confirmModalPage.clickOKButton();
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        assertThat("Successful manufacturer deleted alert should be displayed", this.manufacturerPage.isSuccessAlertDisplayed());
    }*/

    @AfterClass
    @Override
    public void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }
}
