package com.hhistory.test.integration.web.index;

import com.hhistory.test.integration.web.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class IndexPage extends BasePage
{
	@FindBy(id = "index-jumbotron")
	private WebElement indexJumbotron;
	@FindBy(id = "car-pictures-carousel")
	private WebElement indexCarouselLocator;
	@FindBy(className = "carousel-inner")
	private WebElement carouselInnerDivLocator;
	@FindBy(id = "left-arrow-control")
	private WebElement leftArrowControlLocator;
	@FindBy(id = "right-arrow-control")
	private WebElement rightArrowControlLocator;

	public IndexPage(WebDriver webDriver)
	{
		super(webDriver);	
		super.initializePageElements();		
	}
	
    public boolean isJumbotronDisplayed()
    {
    	return this.indexJumbotron.isDisplayed();
    }
    
    public boolean isCarouselDisplayed()
    {
    	return this.indexCarouselLocator.isDisplayed();
    }
    
    public boolean carouselHasValidImages()
    {    	
    	List<WebElement> carouselItems = this.carouselInnerDivLocator.findElements(By.className("item"));	
    	carouselItems.stream()
	                 .filter(this::validateCarouselPicture)
	                 .toList();
    		
    	return !carouselItems.isEmpty();
    }
    
    private boolean validateCarouselPicture(WebElement picture)
    {
		String pictureDivId = picture.getAttribute("id");
		
		if(pictureDivId.contains("pic-div"))
		{
			WebElement pictureImg = picture.findElement(By.cssSelector("img"));
			
			if (!pictureImg.getAttribute("alt").isEmpty())
			{
				if (!pictureImg.getAttribute("src").isEmpty())
				{
					WebElement pictureCaption = picture.findElement(By.className("carousel-caption"));

					return !pictureCaption.getAttribute("innerHTML").isEmpty();
				}
			}
		}
		
		return false;
    }
    
    public boolean carouselHasAnActiveImage()
    {    	
    	WebElement activeImageDiv = this.carouselInnerDivLocator.findElement(By.className("active"));	
    		
    	return activeImageDiv != null;
    }
    
    public String getCarouselActiveImageDivId()
    {    	
    	WebElement activeImageDiv = this.carouselInnerDivLocator.findElement(By.className("active"));	
    		
    	return activeImageDiv.getAttribute("id");
    }
    
    public void clickLeftCarouselControl()
    {
    	if (this.indexCarouselLocator.isDisplayed())
    	{
    		this.leftArrowControlLocator.click();
    	}
    }
    
    public void clickRightCarouselControl()
    {
    	if (this.indexCarouselLocator.isDisplayed())
    	{
    		this.rightArrowControlLocator.click();
    	}
    }
}
