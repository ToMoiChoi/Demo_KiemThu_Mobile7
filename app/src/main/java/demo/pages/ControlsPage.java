package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ControlsPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public ControlsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickControls() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Controls"))).click();
    }

    public void clickLightTheme() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("1. Light Theme"))).click();
    }

    public void enterText(String text) {
        WebElement editField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/edit")));
        editField.clear();
        editField.sendKeys(text);
    }

    public String getEnteredText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/edit"))).getText();
    }
}
