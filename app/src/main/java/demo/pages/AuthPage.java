package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AuthPage {
    private WebDriverWait wait;

    public AuthPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToAuthMain() {
        clickMenu("App");
        clickMenu("Authentication");
        clickMenu("Main");
    }

    private void clickMenu(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"))).click();
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_nav_login"))).click();
    }

    public void clickRegister() {
        // Wait for the button to be visible and clickable to prevent transition timeouts
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_nav_register"))).click();
    }
}
