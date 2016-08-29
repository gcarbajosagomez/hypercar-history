package com.phistory.test.integration.web.cms;

import com.phistory.test.integration.web.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gonzalo on 7/19/16.
 */
public class CMSPage extends BasePage
{
    @FindBy(className = "alert-success")
    private WebElement successAlert;
    @FindBy(className = "alert-danger")
    private WebElement dangerAlert;

    public CMSPage(WebDriver webDriver) {
        super(webDriver);
        super.initializePageElements();
    }

    public boolean isSuccessAlertDisplayed()
    {
        return this.successAlert.isDisplayed();
    }

    public boolean isDangerAlertDisplayed()
    {
        return this.dangerAlert.isDisplayed();
    }
}
