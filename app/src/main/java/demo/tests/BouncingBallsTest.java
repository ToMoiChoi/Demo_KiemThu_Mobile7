package demo.tests;

import demo.pages.BouncingBallsPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BouncingBallsTest {
    private AndroidDriver driver;
    private BouncingBallsPage bouncingBallsPage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2");
        
        File userDir = new File(System.getProperty("user.dir"));
        File projectRoot = userDir.getName().equals("app") ? userDir.getParentFile() : userDir;
        File appFile = new File(projectRoot, "app/build/outputs/apk/debug/app-debug.apk");
        
        if (!appFile.exists()) {
            throw new RuntimeException("APK not found! Run ./gradlew assembleDebug");
        }

        options.setApp(appFile.getAbsolutePath());
        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity(".ApiDemos");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        bouncingBallsPage = new BouncingBallsPage(driver);
        bouncingBallsPage.navigateToBouncingBalls();
    }

    @Test(priority = 1)
    public void testPageOpens() {
        WebElement container = bouncingBallsPage.getAnimationContainer();
        Assert.assertTrue(container.isDisplayed(), "Animation container should be visible");
    }

    @Test(priority = 2)
    public void testMultipleTaps() {
        Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int centerY = size.height / 2;

        // Perform taps in a pattern
        for (int i = 0; i < 5; i++) {
            bouncingBallsPage.tapAtPosition(centerX, centerY);
            sleep(200);
            bouncingBallsPage.tapAtPosition(centerX + 100, centerY + 100);
            sleep(200);
        }
        
        // Since we can't easily verify the dynamic graphics (balls), 
        // we verify the app doesn't crash after multiple interactions.
        Assert.assertTrue(bouncingBallsPage.getAnimationContainer().isDisplayed());
    }

    @Test(priority = 3)
    public void testRandomTaps() {
        Dimension size = driver.manage().window().getSize();
        
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * size.width);
            int y = (int) (Math.random() * size.height);
            bouncingBallsPage.tapAtPosition(x, y);
            sleep(100);
        }
    }

    @Test(priority = 4)
    public void testCreateBallTrail() {
        Dimension size = driver.manage().window().getSize();

        // Vuốt từ góc trên bên trái xuống góc dưới bên phải
        int startX = (int) (size.width * 0.2);
        int startY = (int) (size.height * 0.2);
        int endX = (int) (size.width * 0.8);
        int endY = (int) (size.height * 0.8);

        // Robot thực hiện vuốt để tạo một "dải ngân hà" bóng
        bouncingBallsPage.dragFinger(startX, startY, endX, endY);

        // Sau khi vuốt xong, đợi 1 chút xem hiệu ứng nảy có làm app bị lag không
        sleep(2000);

        // Xác nhận ứng dụng vẫn sống khỏe
        Assert.assertTrue(bouncingBallsPage.getAnimationContainer().isDisplayed());
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
