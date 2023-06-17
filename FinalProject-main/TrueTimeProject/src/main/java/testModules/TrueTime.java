package testModules;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilityClass.HighlightOptions;

import java.util.Set;
import java.util.Iterator;
import java.util.Locale;

public class TrueTime {
	public static WebDriver driver;
	HighlightOptions ho;
	WebDriverWait wait; 
	
	
	public TrueTime(WebDriver driver) {
		TrueTime.driver=driver;
		ho=new HighlightOptions();
		wait= new WebDriverWait(driver, Duration.ofSeconds(120));
	}
	
	//-----------------------------------------------------------------------------------------------------------
	
	
	public void openHomePage() {
		
		
		System.out.println("\n--------------------------------");
		System.out.println("              True Time           ");
		System.out.println("\n--------------------------------");
		String WebSiteURL="https://be.cognizant.com/";
		driver.get(WebSiteURL);	
	}
	
	//----------------------------------------------------------------------------------------------------------------------
		
	public void userVerification() throws InterruptedException {
		
		//this is to verify user and wait till the user profile icon appears on the top right corner of the home page.....
		WebElement profile=wait.until(ExpectedConditions.elementToBeClickable(By.id("O365_HeaderRightRegion")));
		ho.flash(profile,driver);
		profile.click();
	}
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	public void showUserProfileData() throws InterruptedException {
		// to get the name present in the profile 
		WebElement name=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mectrl_currentAccount_primary")));
		ho.flash(name,driver);
		String nameOfEmployee=name.getText();
		
		//to get the email id in the profile
		WebElement email=driver.findElement(By.id("mectrl_currentAccount_secondary"));
		ho.flash(email,driver);
		String emailOfEmployee=email.getText();
		
		//printing name and email in console
		//NOTE: Have to send these information in excel using Apache poi
		System.out.println(nameOfEmployee);
		System.out.println(emailOfEmployee);
		
	}
	
    //--------------------------s---------------------------------------------------------------------------------------------
		
    //		driver.findElement(By.id("spPageCanvasContent")).click();
	
	
	
	    public void clickOneCognizant() {
	    driver.findElement(By.xpath("//*[@id=\"spPageCanvasContent\"]/div/div/div/div/div/div[2]/div/div")).click();
		//for scrolling to one cognizant
		WebElement oneCognizant=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@title='OneCognizant']")));
		oneCognizant.click();
	    }
	    
	   //---------------------------------------------------------------------------------------------------------------------- 
	   
	  public void handlingWindows() {
		
		//handling multiple window
		Set<String> windowsID=driver.getWindowHandles();
		Iterator<String> i=windowsID.iterator();
		String parentID=i.next();
		String childID=i.next();
		driver.switchTo().window(childID);
	    }
		
	  //------------------------------------------------------------------------------------------------------------------------
		
	    public void clickingTrueTime() throws InterruptedException {
		//searching truTime
		WebElement searchBox=wait.until(ExpectedConditions.elementToBeClickable(By.id("oneC_searchAutoComplete")));
		ho.flash(searchBox,driver);
		searchBox.sendKeys("truTime");
		//clicking trutime
		WebElement clickingTruTime=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"newSearchAppsLST\"]/div[1]/div/div[2]")));
		ho.flash(clickingTruTime,driver);
		clickingTruTime.click();	
	    }
	    
	   //-----------------------------------------------------------------------------------------------------------------------------------------------
	    
	    
	    public void monthAndYear() {
	    
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//swithching to iframe
		driver.switchTo().frame("appFrame");
		
		//checking if the month and year is right or not
		String monthAndYear=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"datepicker\"]/div/div/div"))).getText();
		LocalDate currentDate=LocalDate.now();
		DateTimeFormatter monthYearFormatter=DateTimeFormatter.ofPattern("MMMM YYYY");
		String currentMonthYear=currentDate.format(monthYearFormatter);
		
		Assert.assertEquals(monthAndYear, currentMonthYear);
		
		
		//checking if apply for back-dated TopUp is only till 15 days
		//15 days earlier date
		LocalDate earlierDate=currentDate.minusDays(15);
		
		
		//setting the format of current date
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.ENGLISH);
		
		//getting date from website
		String dateInWebSite=driver.findElement(By.xpath("//*[@id=\"mCSB_2_container\"]/div[1]/div[5]/div[1]/div[2]/p/span")).getText();
		
		//changing date according to the format 
		String currentFormattedDate=earlierDate.format(formatter);
		
		Assert.assertEquals(currentFormattedDate,dateInWebSite);
	}
}
