package demo.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Arrays;

public class BouncingBallsPage {
    private AndroidDriver driver;

    public BouncingBallsPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Thực hiện chạm vào màn hình tại tọa độ (x, y) để tạo bóng nảy.
     */
    public void tapOnScreen(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }
}
