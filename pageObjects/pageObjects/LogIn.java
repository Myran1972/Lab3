package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Metoder för att logga in och verifiera inloggning
 * @author Maria
 *
 */
public class LogIn {
	
	WebDriver driver;

	public LogIn(WebDriver d) {
		driver = d;
	}
	 
	 public void setUserName(String user){
	      driver.findElement(By.id("log")).sendKeys(user);
	 }
	 
	 public void setPassword(String pwd){
	      driver.findElement(By.id("pwd")).sendKeys(pwd);
	 }
	 
	 public void clickLogIn() throws InterruptedException{
	      driver.findElement(By.id("login")).click();
	      Thread.sleep(5000);
	 }
	 
	 public String verifyUser(){ //när man loggat in tillkommer en menyrad längst upp, där användarnamn står
		 return driver.findElement(By.id("wp-admin-bar-my-account")).getText();
	 }
}
