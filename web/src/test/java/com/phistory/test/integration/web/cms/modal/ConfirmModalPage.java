package com.phistory.test.integration.web.cms.modal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gonzalo on 7/17/16.
 */
public class ConfirmModalPage extends BaseModalPage
{
    @FindBy(css = "button[" + DATA_BB_HANDLER_HTML_ATTRIBUTE + "='" + CONFIRM_HANDLER + "']")
    private WebElement okButton;
    @FindBy(css = "button[" + DATA_BB_HANDLER_HTML_ATTRIBUTE + "='" + CANCEL_HANDLER + "']")
    private WebElement cancelButton;

    public ConfirmModalPage(WebDriver webDriver)
    {
        super(webDriver);
        super.initializePageElements();
    }

    public boolean isOKButtonDisplayed()
    {
        return this.okButton.isDisplayed();
    }

    public void clickOKButton()
    {
        this.okButton.click();
    }

    public boolean isCancelButtonDisplayed()
    {
        return this.cancelButton.isDisplayed();
    }

    public void clickCancelButton()
    {
        this.cancelButton.click();
    }
}
