package io.github.bonigarcia.wdm;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SauceDemoSecondLargest {
    public static void main(String[] args) throws InterruptedException {
    	 ChromeDriver driver = new ChromeDriver();
         ChromeOptions options = new ChromeOptions();
         options.addArguments("--guest");  // Opens Chrome in guest mode
         WebDriver driver1 = new ChromeDriver(options);
         driver1.get("https://www.saucedemo.com/");
         driver1.manage().window().maximize();
         Thread.sleep(1000);


         driver1.findElement(By.id("user-name")).sendKeys("standard_user");
         driver1.findElement(By.id("password")).sendKeys("secret_sauce");
         driver1.findElement(By.id("login-button")).click();
         Thread.sleep(1000);
         WebElement firstProduct = driver1.findElement(By.className("inventory_item_name"));
         String productName = firstProduct.getText();
         System.out.println("First Product Name: " + productName);

         WebElement firstProductPriceElement = driver1.findElement(By.className("inventory_item_price"));
         String productPriceText = firstProductPriceElement.getText(); // Example: $29.99
         System.out.println("First Product Price: " + productPriceText);

         driver1.findElement(By.xpath("(//button[text()='ADD TO CART'])[1]")).click();
         Thread.sleep(1000);

         driver1.findElement(By.className("shopping_cart_link")).click();
         Thread.sleep(1000);
         String cartPrice = driver1.findElement(By.className("inventory_item_price")).getText();
         if (productPriceText.equals(cartPrice)) {
             System.out.println("✅ Price matched in cart: " + cartPrice);
         } else {
             System.out.println("❌ Price mismatch!");
         }
         driver1.findElement(By.xpath("//a[text()='CHECKOUT']")).click();
         Thread.sleep(1000);

         driver1.findElement(By.id("first-name")).sendKeys("Balaji");
         driver1.findElement(By.id("last-name")).sendKeys("S");
         driver1.findElement(By.id("postal-code")).sendKeys("605001");
         Thread.sleep(1000);
         driver1.findElement(By.xpath("//input[@value='CONTINUE']")).click();
         Thread.sleep(1000);
         List<WebElement> allPrices = driver1.findElements(By.className("inventory_item_price"));
         List<Double> priceList = new ArrayList<Double>();

         for (WebElement price : allPrices) {
             String priceText = price.getText().replace("$", ""); // remove $
             double priceValue = Double.parseDouble(priceText);
             priceList.add(priceValue);
         }
         Collections.sort(priceList, Collections.reverseOrder());
         if (priceList.size() >= 2) {
             System.out.println("Second Largest Price: $" + priceList.get(1));
         } else {
             System.out.println("Only one product found.");
         }
         driver1.findElement(By.xpath("//a[text()='FINISH']")).click();
         Thread.sleep(1000);
         File src = ((TakesScreenshot) driver1).getScreenshotAs(OutputType.FILE);
         File dest = new File("ConfirmationPage.png");
         System.out.println("✅ Screenshot taken: ConfirmationPage.png");
         driver1.quit();
         System.out.println("✅ Browser closed successfully.");
     }
 }