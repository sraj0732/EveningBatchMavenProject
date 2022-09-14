package com.actitime.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
// Imports for Log4j
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;





import com.google.common.io.Files;
public class BaseClass implements ITestListener{
	
	public static String url = "";
	public static WebDriver driver = null;
	
	public static Map<String,String> locatorMap = new HashMap<String,String>();
	public static Map<String,String> testDataMap = new HashMap<String,String>();	
	
	
	
	// create the object of the Logger
	public static Logger logger = (Logger) LogManager.getLogger(BaseClass.class);
	
	
	
	public static void writeInfoLogs(String msg)
	{		
		logger.info(msg);
	}
	

	public static void writeErrorLogs(Throwable t)
	{
		String s = Arrays.toString(t.getStackTrace());		
		String s1 = s.replaceAll(",", "\n");
		logger.error(s1);
		
	}
	
	
	
	
	
	
	//**********************************************************************
	
	/**
	 * Description = This method is to read the config data based on the key value
	 * @param key
	 * @return String Value
	 * @throws IOException
	 * @author Patil
	 */
	
	
	public static String getDataFromPropertiesFile(String key) throws IOException
	{
		String value = "";
		
		File f = new File("./src/test/data/config.properties");
		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of Properties class
		Properties prop = new Properties();
		
		// Loading the data from the properties file
		prop.load(fio);
		
		// Fetch the value of a porperty using its key
		value = prop.getProperty(key);
		
		
		return value;
	}
	
	//***************************************************************************
	
	
	/**
	 * Description = This method is to read the Locator Data from Excel File
	 * @param PageName, ElementName
	 * @return String Value(Xpath)
	 * @throws IOException
	 * @author Patil
	 */

	public static String getLocatorData(String pageName, String elementName) throws IOException
	{
		String locator ="";
		
		File f = new File("./src/test/data/locatordata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//System.out.println(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			if((pageName.equalsIgnoreCase(page)) && (elementName.equalsIgnoreCase(element)))
			{
				locator = ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}		
		}		
		wb.close();	
		return locator;
	}
	
	//*******************************************
	public static String getAndStoreLocatorDataInToMap() throws IOException
	{
		String locator ="";
		
		File f = new File("./src/test/data/locatordata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//System.out.println(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			locator = ws.getRow(x).getCell(2).getStringCellValue();
			
			locatorMap.put(page+"#"+element, locator);
		}	
		wb.close();	
		return locator;
	}
	
	
	public static String getAndStoreTestDataInToMap() throws IOException
	{
		String locator ="";
		
		File f = new File("./src/test/data/testdata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//System.out.println(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			locator = ws.getRow(x).getCell(2).getStringCellValue();
			
			testDataMap.put(page+"#"+element, locator);
		}	
		wb.close();	
		return locator;
	}
	
	
	
	//**************************************************************************
	
	public static String getLocatorDataFromMap(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
			locator = locatorMap.get(pageName+"#"+elementName);
			
		return locator;
		
	}
	
	//************************
	
	
	public static String getTestDataFromMap(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
			locator = testDataMap.get(pageName+"#"+elementName);
			
		return locator;
		
	}

	/**
	 * Description = This method is to read the TestData from Excel File
	 * @param PageName, ElementName
	 * @return String Value(Xpath)
	 * @throws IOException
	 * @author Patil
	 */

	public static String getTestData(String pageName, String elementName) throws IOException
	{
		String data ="";
		
		File f = new File("./src/test/data/testdata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//System.out.println(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			if((pageName.equalsIgnoreCase(page)) && (elementName.equalsIgnoreCase(element)))
			{
				data = ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}		
		}		
		wb.close();	
		return data;
	}
	/**
	 * Title = This method is used for launching the Aplication..
	 */
	
	//*********************************************************
	
	public static String getLocatoDataFromExcel(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
		File f = new File("./src/test/data/locatordata.xlsx");
		FileInputStream fio = new FileInputStream(f);
		
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		int rows = ws.getLastRowNum();
		
		//System.out.println(rows);	
		
		for( int x=1; x<=rows; x++)
		{
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			if((pageName.equalsIgnoreCase(page)) && (elementName.equalsIgnoreCase(element)))
					{
						locator = ws.getRow(x).getCell(2).getStringCellValue();
						break;
					}
			
		}
		
		wb.close();		
		return locator;
		
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public static void launchApplication() throws IOException
	{
		writeInfoLogs("*** This method will run before every Test case*****");
		writeInfoLogs("Starting the method launchApplication");
		url = getDataFromPropertiesFile("url");
		
		String browser = getDataFromPropertiesFile("browser");
		System.out.println("The Test cases will be run using the browser =="+browser);
		
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "./src/test/Utilities/chromedriver.exe");		
			driver = new ChromeDriver();
		}
		
		else if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "./src/test/Utilities/geckodriver.exe");		
			driver = new FirefoxDriver();
		}
		
		String time = getDataFromPropertiesFile("timeout");
		int timeout = Integer.parseInt(time);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));		
		driver.get(url);
		driver.manage().window().maximize();		
		writeInfoLogs("Launched the application...Ending the method launchApplication");
	}
	
	//****************************************************
	/**
	 * 
	 * Title = To write the results to Test File
	 * @param testCaseName
	 * @param status
	 * @throws IOException 
	 * 
	 */
	public static void writeResultsToFile(String testCaseName, String status) throws IOException
	{
		File f = new File("./src/test/results/results.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw = new FileWriter(f,true);
		
		fw.write(testCaseName+"----"+status+"\n");
		fw.flush();
		fw.close();	
		
	}
	
	
	//***************************************************
	
	/**
	 * Title = To write the logs to the file
	 * @throws IOException 
	 * 
	 */
	
	public static void writeLogs1(String str) throws IOException
	{
		
		File f = new File("./src/test/results/logs1.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw = new FileWriter(f,true);
		
		System.out.println("**********"+str+"*************");
		
		fw.write("***********"+str+"**********************\n");
		fw.flush();
		fw.close();	
	}
	
	// To capture the screenshot for failed tests
	
	/**
	 * Title = To capture the screen shot for failed tests
	 */
	
	public static void captureScreenShotOnFailure(String fileName) throws IOException
	{
		
		//Downcast driver to TakeScreenshot level
		TakesScreenshot ts =	((TakesScreenshot)driver);
		
		// Use the getScreenShotAs method to capture the screenshot
		File srcFile =	ts.getScreenshotAs(OutputType.FILE);

		// Create another file where you want  to copy the screenshot captured.
		File destFile = new File("./src/test/results/screenshots/"+fileName+".png");
		
		// To copy the screenshot file to destination...
		Files.copy(srcFile, destFile);

		
	}
	
	// This method is to close the browser on test case completion
	
	@AfterMethod(alwaysRun = true)
	public static void closeBrowser() throws IOException
	{
		writeInfoLogs("*** This method will run after every Test case*****");
		driver.quit();
	}
	
	

	
	  @BeforeTest(alwaysRun = true)
	  public static void beoreTest() 
	  {
	  System.out.println("This method runs before every Test of the TestNG Test"); 
	  }
	  
	  @AfterTest(alwaysRun = true)
	  public static void afterTest() 
	  {
	  System.out.println("This method runs after every Test of the TestNG Test");
	  }
	 
	
	//@BeforeSuite(alwaysRun = true)
	public static void beforeSuite() throws IOException
	{
		
		File f = new File("./results/results.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw = new FileWriter(f);
		
		fw.write("Results for the current run \n");
		fw.flush();
		fw.close();	
		
		
		
		System.out.println("This method is the first method to run");
	}
	
	//@AfterSuite(alwaysRun = true)
	public static void afterSuite()
	{
		System.out.println("This method runs after the entire suite");
	}
	

	
	@BeforeClass(alwaysRun = true)
	public static void beforeClass()
	{
		System.out.println("This method runs before each class");
	}
	
	@AfterClass(alwaysRun = true)
	public static void afterClass()
	{
		System.out.println("This method runs after each class");
	}
	
	
	// ITest Listerner interface default methods.. these will help us to capture the running status of the tests 
	
	public void onStart(ITestContext context) 
	{
		System.out.println(" ##### This is a listener method and this will run in the begining of the entire suite####");
		File f = new File("./src/test/results/results.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("Results for the current run \n");
			fw.flush();
			fw.close();	
		} catch (IOException e1) { 
			e1.printStackTrace();
		}
	
		
		
		// Cleaning up Log files for every run
		
		File f1 = new File("./src/test/results/logs1.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw1;
		try {
			fw1 = new FileWriter(f1);
			fw1.write("Logs for the current run \n");
			fw1.flush();
			fw1.close();	
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		
		
		try {
			getAndStoreLocatorDataInToMap();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}	
	public void onFinish(ITestContext context) {
		writeInfoLogs(" ##### This is a listener method and this will run in at the end of entire suite####");
	}	
	public void onTestStart(ITestResult result) {	
		writeInfoLogs(" Starting the test case "+result.getName());	
	}
	
	public void onTestSuccess(ITestResult result) {	
		try {
			writeResultsToFile(result.getName(), "Pass");
		} catch (IOException e)
		{		
			e.printStackTrace();
		}	
	}
	
	
	public void onTestFailure(ITestResult result) {
		try {
			writeInfoLogs("###### The Test case by name "+result.getName()+" is getting Failed ###");
			writeResultsToFile(result.getName(), "Failed");
			captureScreenShotOnFailure(result.getName());
			writeErrorLogs(result.getThrowable());
			
		} catch (IOException e) {			
			e.printStackTrace();
		}	
	}
	
	
	
}
