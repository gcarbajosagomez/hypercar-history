package com.phistory.test.integration.web.car;

import com.phistory.test.integration.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CarDetailsPage extends BasePage {
	@FindBy(id = "car-pictures-carousel")
	private WebElement carPicturesCarouselLocator;

	public CarDetailsPage(WebDriver webDriver) {
		super(webDriver);
		super.initializePageElements();
	}
	
	public boolean isCarPicturesCarouselDisplayed()
	{
		return this.carPicturesCarouselLocator.isDisplayed();
	}
	
	public boolean carPicturesCarouselHasImages()
	{
		return this.carPicturesCarouselLocator.findElement(By.className("carousel-inner")).isDisplayed();
	}

}
