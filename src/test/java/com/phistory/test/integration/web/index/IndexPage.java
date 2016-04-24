package com.phistory.test.integration.web.index;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage
{
	private WebDriver webDriver;
	
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
	
	public IndexPage() {
		super();
	}

	public IndexPage(WebDriver webDriver) {
		this.webDriver = webDriver;	
		this.initializePageElements();	
	}  
	
	public void initializePageElements()
	{
		PageFactory.initElements(this.webDriver, this);
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
    	carouselItems.stream().filter(item -> this.validateCarouselPicture(item)).collect(Collectors.toList());
    		
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
					
					if (!pictureCaption.getAttribute("innerHTML").isEmpty())
					{
						return true;
					}
				}
			}
		}
		
		return false;
    }
    
    public boolean carouselHasAnActiveImage()
    {    	
    	WebElement activeImage = this.carouselInnerDivLocator.findElement(By.className("active"));	
    		
    	return activeImage != null;
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
