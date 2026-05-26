package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PreferencePage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public PreferencePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickPreferenceDependencies() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("3. Preference dependencies"))).click();
    }

    public void clickWiFiCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("android:id/checkbox"))).click();
    }

    public void clickWiFiSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"WiFi settings\")"))).click();
    }

    public boolean isWiFiSettingsEnabled() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"WiFi settings\")"))).isEnabled();
    }
}
