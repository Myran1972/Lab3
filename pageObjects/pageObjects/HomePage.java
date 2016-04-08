package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Metoder för startsidans funktioner och konstruktor som laddar den
 * Metoder finns för klicka på "My accont-knapp" och logga ut, klicka på "Menyalternativen" för alla produkter
 * eller produktkategori. Även metod för att söka på resultat från dessa "menyalternativ" (vilket kanske borde
 * varit på en annan sida egentligen..?)
 * 
 * @author Maria
 *
 */

public class HomePage {
	WebDriver driver;
	
	public HomePage(WebDriver d){ //konstruktor som öppnar webbsidan inför varje test
		driver=d;
		driver.get("http://www.store.demoqa.com");
	}
	
	public void clickMyAccount(){ 
	    driver.findElement(By.id("account")).click();
	}
	 
	public void LogOut(){ //Används inte i mina testfall, blev inte att jag testade utloggning denna gång
	    driver.findElement(By.id("account_logout")).click();
	}
	 
	public void clickAllProducts(){ //"Menyalternativ"
		driver.findElement(By.xpath("/html/body/div[2]/div/div/header/nav/ul/li[4]/a")).click(); 
	}
	
	public void clickProductCategory(String categoryX) throws InterruptedException{ //"Menyalternativ"
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath("/html/body/div[2]/div/div/header/nav/ul/li[2]/a"));
		action.moveToElement(we).moveToElement(driver.findElement(By.xpath("/html/body/div[2]/div/div/header/nav/ul/li[2]/ul/li[" + categoryX + "]/a"))).click().build().perform();
	}
	
	/**
	 * Nedan kollar resultatet för sökt produkt från "Menyalternativ"
	 * Skriver ut resultat då sidan inte fungera som den borde och visa antingen alla produkter eller
	 * alla produkter av en kategori, som man kan tycka att den borde
	 * @param item
	 */
	
	public void checkItemInProductlist(String item){ //kollar resultatet för sökt produkt från "Menyalternativ"
		 java.util.List<WebElement> listOfProducts = driver.findElements(By.className("wpsc_product_title")); //skapa en lista med produkter som visas
	        String element;
	        for (int i=0; i < listOfProducts.size(); i++){ //jämför om sökt produkt, exakt eller delvis matchar, eller inte finns alls
	        	element = listOfProducts.get(i).getText();
	        	if(element.contains(item)){
	        		System.out.println("Contained " + item + " in product " + element);
	        		if(item.equals(element)){
	        			System.out.println("Product found, stop the search!"); //avbryter om produkten hittats
	        			return;
	        		}
	        	}
	        	else {
	        		System.out.println("Does not contain " + item + " it was instead " +element);
	        	}
	        }
	}
}
