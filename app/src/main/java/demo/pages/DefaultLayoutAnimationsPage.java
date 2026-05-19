package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class DefaultLayoutAnimationsPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public DefaultLayoutAnimationsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("io.appium.android.apis:id/addNewButton"))).click();
    }

    public void clickButtonByIndex(int index) {
        List<WebElement> buttons = driver.findElements(AppiumBy.xpath("//android.widget.GridLayout/android.widget.Button"));
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public int getButtonsCount() {
        List<WebElement> buttons = driver.findElements(AppiumBy.xpath("//android.widget.GridLayout/android.widget.Button"));
        return buttons.size();
    }
}
