package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAccessibility() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Accessibility"))).click();
    }

    public void clickAnimation() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Animation"))).click();
    }

    public void clickText() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text"))).click();
    }

    public void clickViews() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Views"))).click();
    }
}
