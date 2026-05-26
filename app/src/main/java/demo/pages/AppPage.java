package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AppPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public AppPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAlertDialogs() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Alert Dialogs"))).click();
    }
}
