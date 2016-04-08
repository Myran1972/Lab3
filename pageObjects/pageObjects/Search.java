package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Metoder för sökningar i sökfältet
 * Dels för att hitta sökrutan och mata in sökord och dels hitta bland resultaten (eftersom sökningen inte
 * fungerar 100%, söker man specifikt på iPhone 5 t.ex. så får man även upp iPhone 4).
 * Letar sedan länk som stämmer med sökord och klickar på den för att där lägga till i varukorg. 
 * Till sist metoder för om man vill gå till checkout eller fortsätta shoppa.
 * @author Maria
 *
 */
public class Search {
	 WebDriver driver;
	 String item;

		public Search(WebDriver d, String s) {
			driver = d;
			item = s;
		}
		
		public void search(){
			WebElement search = driver.findElement(By.name("s")); 
	        search.sendKeys(item+"\n");
		}
		
		public void clickSearchItem(){
			driver.findElement(By.linkText(item)).click();
		}
		
		public void addItemToChart(){
			driver.findElement(By.className("input-button-buy")).click();
		}
		
		/**
		 * När en vara valts får man alternativ i "pop-up-ruta":
		 */
		
		public void goToCheckout(){
			driver.findElement(By.linkText("Go to Checkout")).click();
		}
		
		public void continueShopping(){
			driver.findElement(By.className("continue_shopping")).click();
		}
}
