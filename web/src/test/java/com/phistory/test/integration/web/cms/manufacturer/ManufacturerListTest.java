package com.phistory.test.integration.web.cms.manufacturer;

import com.phistory.Main;
import com.phistory.test.integration.web.BaseIntegrationTest;
import com.phistory.test.integration.web.cms.login.LoginPage;
import com.phistory.test.integration.web.cms.login.LoginTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.phistory.mvc.controller.cms.CMSBaseController.CMS_CONTEXT;
import static com.phistory.mvc.controller.cms.CMSBaseController.MANUFACTURERS_URL;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gonzalo on 7/19/16.
 */
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@TestExecutionListeners(inheritListeners = false,
        listeners = { DependencyInjectionTestExecutionListener.class,
                      DirtiesContextTestExecutionListener.class })
public class ManufacturerListTest extends BaseIntegrationTest
{
    @Value("${local.server.port}")
    private int port;
    private ManufacturerListPage manufacturerListPage;
    private LoginPage loginPage;
    private LoginTestUtils loginTestUtils;

    @BeforeClass
    @Override
    public void setupTest() throws Exception
    {
        super.setupBaseTest();
        this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT);
        this.loginPage = new LoginPage(this.webDriver);
        this.loginTestUtils = new LoginTestUtils();
        this.loginTestUtils.performCMSLogin(this.loginPage);
        Thread.sleep(STANDARD_TEST_WAIT_MILLIS);
        this.webDriver.get(TEST_SERVER_HOST + this.port + "/" + CMS_CONTEXT + MANUFACTURERS_URL);
        this.manufacturerListPage = new ManufacturerListPage(this.webDriver);
    }

    @Test
    public void test_manufacturer_list_contains_items()
    {
        assertThat("Main Car list div should be displayed", this.manufacturerListPage.isMainCarListDivDisplayed());
        assertThat("Main Car list div should have manufacturers listed", this.manufacturerListPage.hasManufacturersListed());
    }

    @AfterClass
    @Override
    public void tearDownTest()
    {
        if (this.webDriver != null)
        {
            this.webDriver.close();
        }
    }
}
