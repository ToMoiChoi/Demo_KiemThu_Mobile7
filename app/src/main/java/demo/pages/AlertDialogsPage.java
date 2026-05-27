package demo.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
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

    public void clickSingleChoiceDialogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Single choice list"))).click();
    }

    public void clickMultiChoiceDialogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Repeat alarm"))).click();
    }

    public void clickTextEntryDialogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Text Entry dialog"))).click();
    }

    public void clickOkOnDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("android:id/button1"))).click();
    }

    public void clickCancelOnDialog() {
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("android:id/button2"))).click();
    }

    public void selectItemFromList(String itemText) {
        // Sử dụng XPath để tìm item chính xác hơn trong các loại list khác nhau
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//*[@text='" + itemText + "']"))).click();
        // Nghỉ một chút để UI cập nhật trạng thái chọn (Radio/Checkbox)
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public boolean isItemSelected(String itemText) {
        // Tìm element chứa text và kiểm tra thuộc tính 'checked'
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.xpath("//*[@text='" + itemText + "']")));
        String checked = element.getAttribute("checked");
        return "true".equals(checked);
    }

    public String getDialogTitle() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("android:id/alertTitle"))).getText();
    }

    public String getDialogMessage() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.id("android:id/message"))).getText();
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/username_edit"))).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("io.appium.android.apis:id/password_edit"))).sendKeys(password);
    }
}
