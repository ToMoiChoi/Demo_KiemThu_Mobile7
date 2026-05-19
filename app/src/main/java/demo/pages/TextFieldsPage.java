package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TextFieldsPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public TextFieldsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterHelloAppium() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.className("android.widget.EditText")));
        input.sendKeys("Hello Appium");
    }
}
