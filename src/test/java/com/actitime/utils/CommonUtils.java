package com.actitime.utils;

import java.io.IOException;

import org.openqa.selenium.By;

import com.actitime.base.BaseClass;

public class CommonUtils extends BaseClass{
	
	
	public static boolean loginToActiTimeApplication() throws IOException
	{		
		driver.findElement(By.xpath(getLocatoDataFromExcel("LoginPage", "UserNameEditBox"))).
		sendKeys("admin");
		driver.findElement(By.xpath(getLocatoDataFromExcel("LoginPage", "PasswordEditBox"))).
		sendKeys("manager");
		driver.findElement(By.xpath(getLocatoDataFromExcel("LoginPage", "LoginButton"))).click();
		boolean isDisplayed = false;
		try{
			isDisplayed = driver.findElement(By.xpath(getLocatoDataFromExcel("HomePage", "LogoutLink"))).isDisplayed();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isDisplayed;
	}
	
	
	public static boolean loginToActiTimeApplication(String userName, String password) throws IOException
	{
		boolean logoutDisaplyed = false;		
		
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "UserNameEditBox"))).sendKeys(userName);
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "PasswordEditBox"))).sendKeys(password);		
		driver.findElement(By.xpath(getLocatorDataFromMap("LoginPage", "LoginButton"))).click();
						
		try {
			logoutDisaplyed = driver.findElement(By.xpath(getLocatorDataFromMap("HomePage", "LogoutLink"))).isDisplayed();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return logoutDisaplyed;
		
	}
	
	
	public static void selectModule(String moduleName) throws IOException
	{
		String xpath = getLocatorData("HomePage", "ModuleNameTab");
		String xpath1 = xpath.replace("--TEXTREPLACE--", moduleName);
		
		driver.findElement(By.xpath(xpath1)).click();
	}
	
	

}
