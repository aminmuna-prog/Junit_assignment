import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunitAutomation {
    static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Visit Url and check if title is showing properly")


    @Test
    @Order(1)
    public void visitUrl(){
        driver.get("https://demoqa.com/elements");
        String currentUrl = driver.getCurrentUrl();
        String actualResult = driver.getTitle();
        String expectedResult = "DEMOQA";
        Assertions.assertEquals(actualResult, expectedResult);
        Assertions.assertTrue(currentUrl.contains("elements"));

    }

    @Test
    @Order(2)
    public void formAutomation(){
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Test User");
        driver.findElement(By.cssSelector("[type=email]")).sendKeys("test@test.com");
        List<WebElement> addressElement = driver.findElements(By.className("form-control"));
        addressElement.get(2).sendKeys("Gulshan");
        addressElement.get(3).sendKeys("Dhaka");
        Utils.scroll(driver, 500);
        driver.findElements(By.tagName("button")).get(1).click();

        List<WebElement> results = driver.findElements(By.className("mb-1"));

        String val1 = results.get(0).getText();
        String val2 = results.get(1).getText();
        String val3 = results.get(2).getText();
        String  val4 = results.get(3).getText();
        Assertions.assertTrue(val1.contains("Test User"));
        Assertions.assertTrue(val2.contains("test@test.com"));
        Assertions.assertTrue(val3.contains("Gulshan"));
        Assertions.assertTrue(val4.contains("Dhaka"));


    }
@DisplayName("Click on buttons")

@Test
@Order(3)
    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
       List<WebElement> buttonElem = driver.findElements(By.cssSelector("[type = button]"));
        Actions actions = new Actions(driver);
        actions.doubleClick(buttonElem.get(1)).perform();
        actions.contextClick(buttonElem.get(2)).perform();
        actions.click(buttonElem.get(3)).perform();

    }

    @Test
    @Order(4)
    public void handleAlert() throws InterruptedException{
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(7000);
        driver.switchTo().alert().accept();



    }

 @Test
 @Order(5)
    public void datePicker(){
        driver.get("https://demoqa.com/date-picker");
        WebElement txtCalenderElem = driver.findElement(By.id("datePickerMonthYearInput"));
        txtCalenderElem.sendKeys(Keys.CONTROL, "a");
        txtCalenderElem.sendKeys(Keys.BACK_SPACE);
        txtCalenderElem.sendKeys("10/01/2024");
        txtCalenderElem.sendKeys(Keys.ENTER);

    }

    @Test
    @Order(6)
    public void dropDown(){
        driver.get("https://demoqa.com/select-menu");
        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByVisibleText("Black");
        select.selectByIndex(1);

    }

    @Test
    @Order(7)
    public void multipleDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select select = new Select(driver.findElement(By.name("cars")));
        if(select.isMultiple()){
            select.selectByVisibleText("Audi");
            select.selectByVisibleText("Saab");
        }
    }

@Test
@Order(8)
    public void selectSpecialDropdown(){
        driver.get("https://demoqa.com/select-menu");
        (driver.findElement(By.className("css-1hwfws3"))).click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();

    }

@Test
@Order(9)
    public void mouseOver(){
        driver.get("https://daffodilvarsity.edu.bd/");
        List<WebElement> menuElems = driver.findElements(By.className("megamenu-item"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuElems.get(0)).perform();

    }

    @Test
    @Order(10)
    public void mouseHoverUsingXpath(){
        driver.get("https://www.green.edu.bd/");
        WebElement menuEl = driver.findElement(By.xpath("//a[contains(text(), \"About Us\")]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menuEl).click().perform();

    }

    @Test
    @Order(11)
    public void modalDialog(){
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showLargeModal")).click();
        String txt = driver.findElement(By.className("modal-body")).getText();
        System.out.println(txt);
//        driver.findElement(By.id("closeLargeModal")).click();

    }

@Test
@Order(12)
    public void uploadImg(){
        driver.get("https://demoqa.com/upload-download");
//      driver.findElement(By.id("uploadFile")).sendKeys("D://java1.png");
        String relativePath ="\\src\\test\\resources\\java1.png";
        String absolutePath = System.getProperty("user.dir") + relativePath;
       driver.findElement(By.id("uploadFile")).sendKeys(absolutePath);
    }
//

    @Test
    @Order(13)
    public void handlingMultipleTab(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        ArrayList<String> arrayList = new ArrayList(driver.getWindowHandles());
        System.out.println(arrayList);
        driver.switchTo().window(arrayList.get(1));
        String textActual = driver.findElement(By.id("sampleHeading")).getText();
        String textExpect ="This is a sample page";
        Assertions.assertTrue(textActual.contains(textExpect));
        driver.close();
        driver.switchTo().window(arrayList.get(0));

    }

@Test
@Order(14)
    public void handleChildWindow(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("windowButton")).click();
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindow = driver.getWindowHandles();
        Iterator<String> iterator = allWindow.iterator();
        while(iterator.hasNext()){
            String childWindow = iterator.next();
            if(!mainWindow.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String actualText = driver.findElement(By.id("sampleHeading")).getText();
                String expectedText = "This is a sample page";
                Assertions.assertTrue(actualText.contains(expectedText));
            }
        }
        driver.close();
        driver.switchTo().window(mainWindow);
     }

     @Test
    @Order(15)
     public void handlewebTable(){
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.cssSelector("[title=Edit]")).click();
        driver.findElement(By.id("submit")).click();
     }

@Test
    @Order(16)
     public void scrapTableData(){
         driver.get("https://demoqa.com/webtables");
         WebElement table = driver.findElement(By.className("rt-tbody"));
         List<WebElement> allrows = table.findElements(By.cssSelector("[role = rowgroup]"));
         for(WebElement row: allrows){
          List<WebElement> cells  = row.findElements(By.className("rt-td"));
          for (WebElement cell:cells){
              System.out.println(cell.getText());
          }
         }
     }

     @Test
    @Order(17)
     public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

     }
//    public static void main(String[] args) {
//        String relativePath ="\\src\\test\\resources\\java1";
//        String absolutePath = System.getProperty("user.dir") + relativePath;
//        System.out.println(absolutePath);
//
//
//    }
    @AfterAll
    public static void finishTest(){
        driver.quit();
    }
}
