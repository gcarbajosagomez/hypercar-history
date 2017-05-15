package com.phistory.test.integration.web.cms.manufacturer;

import com.phistory.test.integration.web.cms.CMSPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.util.StringUtils;

/**
 * Created by gonzalo on 7/17/16.
 */
public class ManufacturerEditPage extends CMSPage {

    @FindBy(id = "save-manufacturer-button")
    private WebElement saveManufacturerButtonLocator;

    @FindBy(id = "delete-manufacturer-button")
    private WebElement deleteManufacturerButtonLocator;

    @FindBy(className = "btn-default")
    private WebElement newManufacturerLinkLocator;

    @FindBy(id = "editForm.name")
    private WebElement nameInputLocator;

    @FindBy(id = "editForm.nationality")
    private WebElement nationalityInputLocator;

    @FindBy(id = "editForm.historyES")
    private WebElement historyESInputLocator;

    @FindBy(id = "editForm.historyEN")
    private WebElement historyENInputLocator;

    @FindBy(id = "editForm.previewPictureEditCommand.pictureFile")
    private WebElement logoFileInputLocator;

    public ManufacturerEditPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isSaveManufacturerButtonDisplayed() {
        return this.saveManufacturerButtonLocator.isDisplayed();
    }

    public void clickSaveManufacturerButton() {
        this.saveManufacturerButtonLocator.click();
    }

    public boolean isDeleteManufacturerButtonDisplayed() {
        return this.deleteManufacturerButtonLocator.isDisplayed();
    }

    public void clickDeleteManufacturerButton() {
        this.deleteManufacturerButtonLocator.click();
    }

    public boolean isNewManufacturerLinkDisplayed() {
        return this.newManufacturerLinkLocator.isDisplayed();
    }

    public void clickNewManufacturerLink() {
        this.newManufacturerLinkLocator.click();
    }

    public void typeName(String name) {
        this.nameInputLocator.sendKeys(name);
    }

    public boolean nameInputHasContent() {
        return !StringUtils.isEmpty(this.nameInputLocator.getText());
    }

    public void typeNationality(String nationality) {
        this.nationalityInputLocator.sendKeys(nationality);
    }

    public void typeHistoryES(String history) {
        this.historyESInputLocator.sendKeys(history);
    }

    public void typeHistoryEN(String history) {
        this.historyENInputLocator.sendKeys(history);
    }

    public void selectLogoFile(String filePath) {
        this.logoFileInputLocator.sendKeys(filePath);
    }
}
