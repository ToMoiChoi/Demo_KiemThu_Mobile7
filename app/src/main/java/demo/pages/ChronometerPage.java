package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ChronometerPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public ChronometerPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickStart() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Start"))).click();
    }

    public void clickStop() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Stop"))).click();
    }

    public String getTimerText() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/chronometer"))).getText();
    }
}
