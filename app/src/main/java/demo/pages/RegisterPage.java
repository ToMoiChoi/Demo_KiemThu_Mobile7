package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private WebDriverWait wait;

    public RegisterPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        if (text != null && !text.isEmpty()) {
            el.sendKeys(text);
            // Wait until element has text to ensure it's typed
            wait.until(d -> el.getText().length() >= text.length());
        }
    }

    public void enterUsername(String username) {
        type(AppiumBy.id("io.appium.android.apis:id/et_reg_username"), username);
    }

    public void enterEmail(String email) {
        type(AppiumBy.id("io.appium.android.apis:id/et_reg_email"), email);
    }

    public void enterPassword(String password) {
        type(AppiumBy.id("io.appium.android.apis:id/et_reg_password"), password);
    }

    public void enterConfirmPassword(String password) {
        type(AppiumBy.id("io.appium.android.apis:id/et_reg_confirm_password"), password);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_reg_submit"))).click();
    }

    public void clickBack() {
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("io.appium.android.apis:id/btn_reg_back"))).click();
    }

    public String getUsernameError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_reg_username"));
    }

    public String getEmailError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_reg_email"));
    }

    public String getPasswordError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_reg_password"));
    }

    public String getConfirmPasswordError() {
        return getError(AppiumBy.id("io.appium.android.apis:id/et_reg_confirm_password"));
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
