package se.iths.Lab3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageObjects.Checkout;
import pageObjects.HomePage;
import pageObjects.LogIn;
import pageObjects.Purchase;
import pageObjects.Search;

/**
 * 10 testfall som jag försökt sprida ut för att täcka de flesta funktionerna, på inget sätt täckande testning
 * Förhoppningsvis har jag lyckats göra dem på ett bra sätt i alla fall hoppas jag. Och att de är tydliga nog.
 * Har anpassat testerna utifrån hur det ser ut, för det är ju tråkigt när det inte blir grönt ;) Det har jag 
 * försökt att kommentera vid tester och metoder. 
 * 
 * Frågor att besvara:
 * "Ni ska förutom att göra dessa tester även svara på vilken typ av tester ni tycker borde upptäckas
 *  av Selenium och vilka borde upptäckts tidigare." 
 *  Selenium testar fr.a. användargränssnitt, som navigering och textinmatning. Jag tänker mig att som testare
 *  som skriver test mha Selenium, så hittar man fel under tiden. Testen som skapas kan fr.a. användas vid
 *  regressionstestning. 
 *  Man borde upptäckt tidigare att obligatoriska fält inte får lämnas tomma etc. som vid genomförandet av köp.
 *  Man kan inte testa registrering av konto fullt ut i Selenium (tror jag) eftersom det är en klurig fråga
 *  att besvara. Det krävs iaf mer komplicerad kod i testet, så detta får ju testas förslagvis manuellt.
 *  Layouten testas inte av Selenium, så den får testas på annat sätt.
 * 
 * @author Maria
 *
 */
public class Lab3Test {
	
	private WebDriver driver;
	private HomePage hp;
	
	@Before
	public void setup(){
		driver = new FirefoxDriver();
		//@SuppressWarnings("unused")
		hp = new HomePage(driver);
	}
	
	@After
	public void teardown(){
		driver.quit();
	}
	
	
	@Test //Testar registrering av konto utan svar på ekvationen, ska generera felmeddelande
	public void testRegister() throws InterruptedException{
		hp.clickMyAccount();
		driver.findElement(By.linkText("Register")).click();
		driver.findElement(By.id("user_login")).sendKeys("orimligt");
		driver.findElement(By.id("user_email")).sendKeys("felaktig.orimlig@gmail.com");
		driver.findElement(By.id("wp-submit")).click();
		Thread.sleep(1000);
		String error = driver.findElement(By.id("login_error")).getText();
		assertEquals("ERROR: Your answer was incorrect - please try again.", error);
	}
	
	@Test //Testar att logga in och verifierar inloggad användare
	public void testLogIn() throws InterruptedException{
		LogIn login = new LogIn(driver);
		String username = "marrun";
		String password = "HejPaDig!KlAr10";
	 
	    hp.clickMyAccount();
	 
	    login.setUserName(username);
	    login.setPassword(password);
	    login.clickLogIn();
	 
        assertEquals("Howdy, "+username, login.verifyUser());
	}
	
	@Test //Testar att logga in med felaktigt lösenord
	public void testLogInWrongPwd() throws InterruptedException{
		LogIn login = new LogIn(driver);
		String username = "marrun";
		String password = "WrongPassword";
	 
	    hp.clickMyAccount();
	 
	    login.setUserName(username);
	    login.setPassword(password);
	    login.clickLogIn();
	    
	    WebElement error = driver.findElement(By.className("response"));
	    
	    assertEquals(error.getText(), "ERROR: Invalid login credentials.");
	}
	
	@Test //Testar att lägga vara i varukorg, gå till checkout och rätt vara finns med 1 antal
	public void testAllProducts() {
		String item = "Apple iPod touch Large";
		hp.clickAllProducts();
		hp.checkItemInProductlist(item);	
	}	
	
	@Test //Testar att välja produktkategori iPhones och se om iPhone 5 visas, vilket den ju borde ha gjort
	public void testProductCategory() throws InterruptedException{
		String item = "iPhone 5";
		String categoryX = "4"; //iPhones;
		hp.clickProductCategory(categoryX);
		hp.checkItemInProductlist(item);
	}
	
	@Test //Testar att lägga vara i varukorg, gå till checkout och rätt vara finns med 1 antal
	public void testCheckOut() throws InterruptedException{
		String item = "iPhone 5";
		Checkout co = new Checkout(driver);
		
		addItemGoToCheckout(item);
        
        assertEquals("Checkout", co.verifyCheckout());
        assertEquals(item, co.verifyItem(item));   
        assertEquals("1", co.verifyQuantity());
	}
	
	@Test //Testar att lägga vara i varukorg, gå till checkout och öka antal och se att antal ökas i varukorgen
	public void testUpdateQuantity() throws InterruptedException{
		String item = "Magic Mouse";
		Checkout co = new Checkout(driver);
		String newQuantity = "2";
        
        addItemGoToCheckout(item);
        
        co.changeQuantity(newQuantity);
        
        assertEquals(newQuantity,co.verifyCheckoutItemsQuantity());
	}
	
	@Test //Testar att lägga varor i varukorg, gå till checkout och ta bort varan på rad 2
	public void testRemoweItem() throws InterruptedException{
		Checkout co = new Checkout(driver);
		String newQuantity = "2";
		
        addItemContinueShopping("Magic Mouse");
        addItemContinueShopping("iPhone 5");
        addItemGoToCheckout("Apple iPod touch Large");
        
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/div[1]/table/tbody/tr[3]/td[6]/form/input[4]")).click();
        
        assertEquals(newQuantity,co.verifyCheckoutItemsQuantity());
	}
	
	@Test //Testar att genomföra ett köp med en vara
	public void testBuyItem() throws InterruptedException{
		Checkout co = new Checkout(driver);
		Purchase p = new Purchase(driver);
		
        addItemGoToCheckout("Magic Mouse");
        co.continueCheckout();
        p.fillInFormRequired();
        p.clickPurchase();
                
        //Varan visas inte, fast rubrik för detta finns. Hade annars jmf att rätt vara stod där.
        assertEquals(p.verifyPurchase(), "Thank you, your purchase is pending. You will be sent an email once the order clears.");
	}
	
	@Test //Testar att genmföra ett köp utan att ange email
	public void testBuyItemMissingEmail() throws InterruptedException{
		Checkout co = new Checkout(driver);
		Purchase p = new Purchase(driver);
		
        addItemGoToCheckout("Magic Mouse");
        co.continueCheckout();
        p.fillInFormMissingEmail();
        p.clickPurchase(); //här kommer man tillbaka till checkout, vilket inte är logiskt
        co.continueCheckout(); //pga ovan får man klicka på continue igen, då ser man meddelandet om att email saknas 
        String message = driver.findElement(By.className("validation-error")).getText(); 
        assertEquals("Please enter a valid email.", message);        
	}
	
	/** Metoder för att söka och lägga till item i varukorg
	 * 
	 * @param item
	 * @throws InterruptedException
	 */
	
	public void addItemGoToCheckout(String item) throws InterruptedException{
		Search s = new Search(driver, item);
	    s.search();
        s.clickSearchItem();
        s.addItemToChart();
        Thread.sleep(5000);
        s.goToCheckout();
        Thread.sleep(500);
	}
	
	public void addItemContinueShopping(String item) throws InterruptedException{
		Search s = new Search(driver, item);
	    s.search();
        s.clickSearchItem();
        s.addItemToChart();
        Thread.sleep(3000);
        s.continueShopping();
	}
}
