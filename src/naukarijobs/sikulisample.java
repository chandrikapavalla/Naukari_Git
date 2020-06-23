package naukarijobs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class sikulisample {
	
	public static void main(String []args)
	{
		
		System.setProperty("webdriver.chrome.driver", "E:/drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.navigate().to("https://www.naukri.com/fresher-jobs-in-india?k=fresher&l=india");
		
		
		
		
		
	}

}
