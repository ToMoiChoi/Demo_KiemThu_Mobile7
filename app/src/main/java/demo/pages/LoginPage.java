package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriverWait wait;

    public LoginPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        if (text != null && !text.isEmpty()) {
            el.sendKeys(text);
            // Verify text to prevent "click before type"
            wait.until(d -> el.getText().length() >= text.length());
        }
    }

    public void enterUsername(String username) {
        type(AppiumBy.id("io.appium.android.apis:id/et_login_username"), username);
    }

    public void enterPassword(String password) {
        type(AppiumBy.id("io.appium.android.apis:id/et_login_password"), password);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_login_submit"))).click();
    }

    public void clickBack() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_login_back"))).click();
    }

    public String getUsernameError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_login_username"));
    }

    public String getPasswordError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_login_password"));
    }

    private String getError(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return el.getAttribute("errorText");
        } catch (Exception e) {
            return null;
        }
    }
}
