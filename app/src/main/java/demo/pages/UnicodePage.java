package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class UnicodePage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public UnicodePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickUnicodeMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Unicode"))).click();
    }

    public boolean isUnicodeTextVisible(String text) {
        List<WebElement> elements = driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + text + "\")"));
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }
}
