package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AnimationPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public AnimationPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickDefaultLayoutAnimations() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Default Layout Animations"))).click();
    }

    public void clickEvents() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Events"))).click();
    }

    public void clickHideShowAnimations() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Hide-Show Animations"))).click();
    }
}
