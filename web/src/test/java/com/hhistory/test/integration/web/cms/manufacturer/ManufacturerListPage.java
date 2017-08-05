package com.hhistory.test.integration.web.cms.manufacturer;

import com.hhistory.test.integration.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gonzalo on 7/19/16.
 */
public class ManufacturerListPage extends BasePage
{
    @FindBy(id = "main-manufacturer-list-div")
    private WebElement mainCarListDivLocator;

    public ManufacturerListPage(WebDriver webDriver) {
        super(webDriver);
        super.initializePageElements();
    }

    public boolean isMainCarListDivDisplayed()
    {
        return this.mainCarListDivLocator.isDisplayed();
    }

    public boolean hasManufacturersListed()
    {
        return !this.mainCarListDivLocator.findElements(By.className("preview-img")).isEmpty();
    }
}
