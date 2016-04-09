package com.phistory.test.integration.web.index;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.phistory.test.integration.web.BasePage;

@Component
public class IndexPage extends BasePage
{
	@FindBy(id = "index-jumbotron")
	private WebElement indexJumbotronLocator;
	@FindBy(id = "car-pictures-carousel")
	private WebElement indexCarouselLocator;
	@FindBy(className = "carousel-inner")
	private WebElement carouselInnerDivLocator;
	@FindBy(id = "left-arrow-control")
	private WebElement leftArrowControlLocator;
	@FindBy(id = "right-arrow-control")
	private WebElement rightArrowControlLocator;
	boolean carouselHasValidImages = true;

	public void initializePage() throws Exception
	{
		getWebDriver().get(getBaseUrl() + "/index.html");
        
		String loginPageTitle = getMessageSource().getMessage("title.index", null, Locale.ENGLISH);
		
		if (loginPageTitle.contains(getWebDriver().getTitle()))
		{
            throw new IllegalStateException("This is not the index page");
        }
		
		PageFactory.initElements(getWebDriver(), this);
	}

    public IndexPage checkJumbotronIsDisplayed()
    {
    	WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
    	wait.until(ExpectedConditions.elementToBeClickable(indexJumbotronLocator));
    	
    	if (indexJumbotronLocator.isDisplayed())
    	{
    		return this;  
    	}
    	
    	return null;
    }
    
    public IndexPage checkCarouselIsDisplayed()
    {
    	WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
    	wait.until(ExpectedConditions.elementToBeClickable(indexCarouselLocator));
    	
    	if (indexCarouselLocator.isDisplayed())
    	{
    		return this;  
    	}
    	
    	return null;
    }
    
    public IndexPage checkCarouselHasValidImages()
    {
    	WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
    	wait.until(ExpectedConditions.elementToBeClickable(indexCarouselLocator));
    	
    	if (carouselInnerDivLocator.isDisplayed())
    	{	
    		carouselInnerDivLocator.findElements(By.className("item")).parallelStream().forEach(item -> validateCarouselPicture(item));
    		
    		if (carouselHasValidImages) return this;  
    	}
    	
    	return null;
    }
    
    private void validateCarouselPicture(WebElement picture)
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
					
					if (pictureCaption.getAttribute("innerHTML").isEmpty())
					{
						carouselHasValidImages = false;
					}
				}
			}
		}
    }
    
    public IndexPage clickLeftCarouselControl()
    {
    	if (indexCarouselLocator.isDisplayed())
    	{
    		leftArrowControlLocator.click();
    	}
    	
    	return null;
    }
    
    public IndexPage clickRightCarouselControl()
    {
    	if (indexCarouselLocator.isDisplayed())
    	{
    		rightArrowControlLocator.click();
    	}
    	
    	return null;
    }
}
