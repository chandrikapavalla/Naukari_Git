package naukarijobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FresherJobs {

	public static WebDriver driver;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "E:/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.navigate().to("https://www.naukri.com/fresher-jobs-in-india?k=fresher&l=india");
		implicitWait(3);
		System.out.println("successfully opened");
		driver.findElement(By.cssSelector("div[class='sortAndH1Cont'] p[class='sort-droop-label']")).click();
		List<WebElement> sortby = driver.findElements(By.cssSelector(".sort-droop-label+ul>li"));
		Iterator<WebElement> itr = sortby.iterator();

		while (itr.hasNext()) {
			WebElement ele = (WebElement) itr.next();
			String element = ele.getText();
			System.out.println(element);
			if (element.equalsIgnoreCase("date")) {
				ele.click();
				implicitWait(3);
				break;
			}
		}

		jobPages();
		System.out.println("firstpage finished----------------------------------------------->");	
			
		driver.findElement(By.xpath("//div[@class='fleft pages']/a[2]")).click();
		System.out.println("Entered into second page-------------------------------------------->");
		jobPages();
		
		driver.findElement(By.xpath("//div[@class='fleft pages']/a[3]")).click();
		System.out.println("entered into third page---------------------------------------> ");
		jobPages();
		driver.findElement(By.xpath("//div[@class='fleft pages']/a[4]")).click();
		jobPages();
		
		
		
		
		
		
		}


	/**
	 * 
	 */
	public static void jobPages() {
		List<WebElement> articles = driver.findElements(By.cssSelector(
				"section[class='listContainer fleft']  div[class='info fleft'] a[class='title fw500 ellipsis']"));
		implicitWait(3);
		System.out.println("Articles size : -----------------------------> " + articles.size());
		String parent = driver.getWindowHandle();

		for (int i = 0; i < articles.size(); i++)

		{
			

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", articles.get(i));
			implicitWait(2);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", articles.get(i));
			implicitWait(5);

			ArrayList<String> child = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(child.get(i + 1));
			try {
               WebElement jobheader=driver.findElement(By.cssSelector("section[class='jd-header'] "));
               implicitWait(2);
               String header=jobheader.getText();
               implicitWait(2);
               System.out.println(header);
               updateExcelSheet(header, i);
               implicitWait(2);
				WebElement jobDescription = driver.findElement(By.cssSelector("section[class='job-desc']"));
				String post="Just now";
				WebElement posted =driver.findElement(By.xpath("//span[text()='Just now']"));
				System.out.println(posted.getText());
				if(posted.getText().equalsIgnoreCase(post))
				{
				String description = jobDescription.getText();
				
				System.out.println("Sheet description : --------------------->" + description);
				

				updateExcelSheet(description,i);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			implicitWait(3);
			driver.switchTo().window(parent);
			implicitWait(2);
           
			
			
		}
	}
	

	/**
	 * @param description
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void updateExcelSheet(String description,int i) throws FileNotFoundException, IOException {
		FileInputStream input = new FileInputStream(new File("jobs.xlsx"));
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheet("Sheet1");
		System.out.println(sheet.getPhysicalNumberOfRows());
		
		
		//Row desc1 = sheet.getRow(0);
		Row desc = sheet.getRow(i+1);

		Cell val = sheet.getRow(i+1).createCell(1);
		val.setCellValue(description);
		input.close();
		FileOutputStream outputStream = new FileOutputStream("jobs.xlsx");
		wb.write(outputStream);
		outputStream.close();
	}

	/**
	 * @param driver
	 */
	public static void implicitWait(int i) {
		driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
	}

}
