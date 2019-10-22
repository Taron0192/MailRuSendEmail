import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class MailRuTestCases {

    // TODO load from properties
    private static final String USERNAME = "tarontest.qualitylab";
    private static final String PASSWORD = "123tarolitylab123";
    private static final String MAIL_RU_URL = "http://www.mail.ru";
    private static final String DOMAIN = "@bk.ru";
    private static final String TEXT_INPUT_XPATH = "/html/body/div[14]/div[2]/div/div[1]/div[2]/div[3]/div[5]/div/div/div[2]/div[1]/div/div[1]";
    private static final String SEND_BUTTON_XPATH = "/html/body/div[14]/div[2]/div/div[2]/div[1]/span[1]";
    private static final String EMAIL_TO_SEND_NAME = "container--H9L5q";
    private static final String SOME_TEXT = "some text";
    private static final String NEW_EMAIL_BUTTON_NAME = "sidebar__compose-btn-box";
    private static final String LOGIN_INPUT_ID = "mailbox:login";
    private static final String DOMAIN_ID = "mailbox:domain";
    private static final String SUBMIT_BUTTON_ID = "mailbox:submit";
    private static final String PASSWORD_INPUT_ID = "mailbox:password";


    private WebDriver driver;

    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void test() {
        driver.navigate().to(MAIL_RU_URL);

        // Login
        fillInputById(LOGIN_INPUT_ID, USERNAME);
        selectDomain(DOMAIN_ID, DOMAIN);
        clickElementById(SUBMIT_BUTTON_ID);
        fillInputById(PASSWORD_INPUT_ID, PASSWORD);
        clickElementById(SUBMIT_BUTTON_ID);

        //Send email
        clickAndWaitElementNewMessage(NEW_EMAIL_BUTTON_NAME);
        fillInputByClass(EMAIL_TO_SEND_NAME, USERNAME + DOMAIN);
        findAndFillElementByXpath(TEXT_INPUT_XPATH, SOME_TEXT);
        findAndClickElementByXpath(SEND_BUTTON_XPATH);

    }

    @AfterTest
    public void terminateBrowser() {
        driver.quit();
    }

    private void findAndFillElementByXpath(final String path, final String value) {
        driver.findElement(By.xpath(path)).sendKeys(value);
    }

    private void findAndClickElementByXpath(final String path) {
        driver.findElement(By.xpath(path)).click();
    }


    private void selectDomain(final String id, final String domain) {
        new Select(driver.findElement(By.id(id))).selectByVisibleText(domain);
    }

    private void clickElementById(final String id) {
        driver.findElement(By.id(id)).click();
    }

    private void clickAndWaitElementNewMessage(final String name) {
        WebElement element = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.className(name)));
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        element.click();
    }

    private void fillInputById(final String inputId, final String value) {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.id(inputId))));
        element.clear();
        element.sendKeys(value);
    }

    private void fillInputByClass(final String name, final String value) {
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.className(name))));
        element.clear();
        element.sendKeys(value);
    }

}
