package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccessibilityPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public AccessibilityPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAccessibilityNodeProviderVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    AppiumBy.accessibilityId("Accessibility Node Provider"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
