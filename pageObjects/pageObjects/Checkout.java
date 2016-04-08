package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Metoder för Checkout, första delsidan
 * Verifiering av rätt sida, rätt produkt, rätt antal
 * Ändra antal och gå vidare för att genomföra köp
 * @author Maria
 *
 */
public class Checkout {
	WebDriver driver;

	public Checkout(WebDriver d) {
		driver = d;
	}
	
	public String verifyCheckout(){
		return driver.findElement(By.className("entry-title")).getText();
	}
	
	public String verifyItem(String item){
		return driver.findElement(By.linkText(item)).getText();
	}
	
	public String verifyQuantity(){
		 return driver.findElement(By.name("quantity")).getAttribute("value");
	}
	
	public void changeQuantity(String x){
		driver.findElement(By.name("quantity")).clear();
		driver.findElement(By.name("quantity")).sendKeys(x);
		driver.findElement(By.name("submit")).click();
	}
	
	public String verifyCheckoutItemsQuantity(){
		return driver.findElement(By.className("count")).getText();
	}
	
	public void continueCheckout() throws InterruptedException{ //går vidare med köp
		driver.findElement(By.className("step2")).click(); 
        Thread.sleep(2000);
	}
}
