package SeleniumExcersice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class loginPage {

	public static void main(String[] args) {
	String driverpath = System.getProperty("user.dir")+"/DriverFiles/chromedriver.exe";
	System.setProperty("webdriver.chrome.driver", driverpath);
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	driver.get("https://dev.ipactsolutions.com/");
	
	driver.findElement(By.id("userName")).sendKeys("KLM_Adminuser");
	driver.findElement(By.id("password")).sendKeys("123@Abcd");
	driver.findElement(By.id("submit")).click();
}
}
