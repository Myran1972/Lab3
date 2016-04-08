package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Metoder för genomföra ett köp sidan, där alla uppgifter fylls i
 * Dels för att fylla i alla fält som krävs och en där email saknas
 * Därefter metoder med att genomföra köpet och kontrollera att så skedde (så gott det går, hade velat verifiera
 * att det var rätt produkt också, men den visas inte)
 * @author Maria
 *
 */
public class Purchase {
	WebDriver driver;

	public Purchase(WebDriver d) {
		driver = d;
	}
	
	public void fillInFormRequired(){
		//email
        driver.findElement(By.name("collected_data[9]")).sendKeys("kalle@mail.com");
        //firstname 
        driver.findElement(By.name("collected_data[2]")).sendKeys("Kalle");
        //lastname 
        driver.findElement(By.name("collected_data[3]")).sendKeys("Anka");
        //adress 
        driver.findElement(By.name("collected_data[4]")).sendKeys("Ankgatan 5");
        //city 
        driver.findElement(By.name("collected_data[5]")).sendKeys("Ankeborg");
        //state 
        driver.findElement(By.name("collected_data[6]")).sendKeys("Disneyland");
        //country 
        Select drpCountry = new Select(driver.findElement(By.name("collected_data[7][0]")));
        drpCountry.selectByVisibleText("Sweden");
        //postnr 
        //ej obligatoriskt tydligen, men borde ju vara om man tänker på postgången
        driver.findElement(By.name("collected_data[8]")).sendKeys("12345");
        //phone 
        driver.findElement(By.name("collected_data[18]")).sendKeys("070-6584174");
        //checkruta för samma lev.adress, inte heller obligatorisk, men "undefined" fält med State/Province är markerad *, dock går köp igenom utan att fylla i
        driver.findElement(By.id("shippingSameBilling")).click();
	}
	
	public void fillInFormMissingEmail(){
        driver.findElement(By.name("collected_data[2]")).sendKeys("Kalle");
        driver.findElement(By.name("collected_data[3]")).sendKeys("Anka");
        driver.findElement(By.name("collected_data[4]")).sendKeys("Ankgatan 5");
        driver.findElement(By.name("collected_data[5]")).sendKeys("Ankeborg");
        driver.findElement(By.name("collected_data[6]")).sendKeys("Disneyland");
        Select drpCountry = new Select(driver.findElement(By.name("collected_data[7][0]")));
        drpCountry.selectByVisibleText("Sweden");
        driver.findElement(By.name("collected_data[18]")).sendKeys("070-6584174");
	}
	
	public void clickPurchase(){
		 driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/div[2]/div/form/div[4]/div/div/span/input")).click();
	}
	
	public String verifyPurchase(){
		return driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/p[1]")).getText();
	}
}
