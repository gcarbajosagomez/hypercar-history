package com.hhistory.test.integration.web;

import com.hhistory.mvc.manufacturer.Manufacturer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.hhistory.mvc.manufacturer.Manufacturer.*;

public abstract class BaseIntegrationTest extends AbstractTestNGSpringContextTests {

    protected static final Manufacturer IRRELEVANT_MANUFACTURER            = PAGANI;
    protected static final String       TEST_SERVER_HOST                   = "http://localhost:";
    protected static final long         STANDARD_TEST_WAIT_MILLIS          = 2000;
    protected static final String       PROJECT_PATH                       = "/home/gonzalo/workspace/pagani-history-web";
    private static final   String       CHROME_DRIVER_SYSTEM_PROPERTY_NAME = "webdriver.chrome.driver";

    protected WebDriver webDriver;

    @BeforeClass
    public abstract void setupTest() throws Exception;

    protected abstract String getTestUrl();

    protected void setupBaseTest() throws Exception {
        System.setProperty(CHROME_DRIVER_SYSTEM_PROPERTY_NAME,
                           "src/test/resources/com/hhistory/test/integration/web/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        this.webDriver = new ChromeDriver(options);
    }

    @AfterClass
    public abstract void tearDownTest();
}
