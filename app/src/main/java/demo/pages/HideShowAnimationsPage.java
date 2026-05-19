package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HideShowAnimationsPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public HideShowAnimationsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickButton(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"))).click();
    }

    public boolean isButtonVisible(String text) {
        List<WebElement> buttons = driver.findElements(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"));
        return !buttons.isEmpty() && buttons.get(0).isDisplayed();
    }

    public void clickShowButtons() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("io.appium.android.apis:id/addNewButton"))).click();
    }

    public void clickCustomAnimations() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("io.appium.android.apis:id/customAnimCB"))).click();
    }

    public void clickHideGone() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("io.appium.android.apis:id/hideGoneCB"))).click();
    }

    public boolean isCustomAnimationsChecked() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/customAnimCB"))).getAttribute("checked").equals("true");
    }

    public boolean isHideGoneChecked() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/hideGoneCB"))).getAttribute("checked").equals("true");
    }
}
