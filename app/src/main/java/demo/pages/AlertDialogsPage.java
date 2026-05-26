package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AlertDialogsPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public AlertDialogsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOkCancelDialogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("OK Cancel dialog with a message"))).click();
    }

    public void clickListDialogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("List dialog"))).click();
    }

    public void clickOkOnDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("android:id/button1"))).click();
    }

    public void selectItemFromList(String itemText) {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + itemText + "\")"))).click();
    }
}
