package demo.tests;

import demo.pages.*;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Lớp ApiDemosPOMTest thực hiện các bài kiểm thử tự động (Test Cases)
 * được sắp xếp theo trình tự logic của ứng dụng (từ 01 đến 17).
 */
public class ApiDemosPOMTest {
    private AndroidDriver driver;
    private HomePage homePage;
    private AccessibilityPage accessibilityPage;
    private AnimationPage animationPage;
    private BouncingBallsPage bouncingBallsPage;
    private DefaultLayoutAnimationsPage defaultLayoutAnimationsPage;
    private HideShowAnimationsPage hideShowAnimationsPage;
    private ControlsPage controlsPage;
    private ViewsPage viewsPage;
    private TextFieldsPage textFieldsPage;
    private AppPage appPage;
    private AlertDialogsPage alertDialogsPage;
    private PreferencePage preferencePage;
    private ChronometerPage chronometerPage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2"); 
        
        File userDir = new File(System.getProperty("user.dir"));
        File projectRoot = userDir.getName().equals("app") ? userDir.getParentFile() : userDir;
        File appFile = new File(projectRoot, "app/build/outputs/apk/debug/app-debug.apk");
        
        if (!appFile.exists()) {
            throw new RuntimeException("Không tìm thấy APK tại: " + appFile.getAbsolutePath());
        }

        options.setApp(appFile.getAbsolutePath());
        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity(".ApiDemos");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

        // Khởi tạo tất cả các đối tượng Page (POM)
        homePage = new HomePage(driver);
        accessibilityPage = new AccessibilityPage(driver);
        animationPage = new AnimationPage(driver);
        bouncingBallsPage = new BouncingBallsPage(driver);
        defaultLayoutAnimationsPage = new DefaultLayoutAnimationsPage(driver);
        hideShowAnimationsPage = new HideShowAnimationsPage(driver);
        controlsPage = new ControlsPage(driver);
        viewsPage = new ViewsPage(driver);
        textFieldsPage = new TextFieldsPage(driver);
        appPage = new AppPage(driver);
        alertDialogsPage = new AlertDialogsPage(driver);
        preferencePage = new PreferencePage(driver);
        chronometerPage = new ChronometerPage(driver);
    }

    // =========================================================================
    // NHÓM 1: ACCESSIBILITY (PRIORITY 1)
    // =========================================================================

    @Test(priority = 1)
    public void test01_AccessibilityNodeProvider() {
        System.out.println("TEST 01: Kiểm tra Accessibility Node Provider");
        homePage.clickAccessibility();
        Assert.assertTrue(accessibilityPage.isAccessibilityNodeProviderVisible(), "Menu Accessibility Node Provider không hiển thị!");
        resetApp();
    }

    // =========================================================================
    // NHÓM 2: ANIMATION (PRIORITY 2-7)
    // =========================================================================

    @Test(priority = 2)
    public void test02_DefaultLayoutAdd() {
        System.out.println("TEST 02: Thêm button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        int initial = defaultLayoutAnimationsPage.getButtonsCount();
        defaultLayoutAnimationsPage.clickAddButton();
        sleep(800);
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), initial + 1, "Số lượng button không tăng sau khi thêm!");
        resetApp();
    }

    @Test(priority = 3)
    public void test03_DefaultLayoutRemove() {
        System.out.println("TEST 03: Xóa button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        defaultLayoutAnimationsPage.clickAddButton();
        sleep(800);
        int count = defaultLayoutAnimationsPage.getButtonsCount();
        defaultLayoutAnimationsPage.clickButtonByIndex(0);
        sleep(800);
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), count - 1, "Số lượng button không giảm sau khi xóa!");
        resetApp();
    }

    @Test(priority = 4)
    public void test04_DeleteAllButtons() {
        System.out.println("TEST 04: Xóa sạch toàn bộ button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        for(int i=0; i<3; i++) {
            defaultLayoutAnimationsPage.clickAddButton();
            sleep(200);
        }
        defaultLayoutAnimationsPage.clearAllButtons();
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), 0, "Vẫn còn button sau khi đã xóa hết!");
        resetApp();
    }

    @Test(priority = 5)
    public void test05_HideShowInvisible() {
        System.out.println("TEST 05: Ẩn button chế độ Invisible");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"), "Button vẫn hiển thị ở chế độ Invisible!");
        resetApp();
    }

    @Test(priority = 6)
    public void test06_HideShowGone() {
        System.out.println("TEST 06: Ẩn button chế độ GONE");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (!hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"), "Button vẫn hiển thị ở chế độ GONE!");
        resetApp();
    }

    @Test(priority = 7)
    public void test07_BouncingBalls() {
        System.out.println("TEST 07: Tạo bóng nảy (Bouncing Balls)");
        homePage.clickAnimation();
        animationPage.clickBouncingBalls();
        bouncingBallsPage.tapOnScreen(500, 500);
        bouncingBallsPage.tapOnScreen(200, 800);
        sleep(1000);
        resetApp();
    }

    // =========================================================================
    // NHÓM 3: APP & ALERT DIALOGS (PRIORITY 8-12)
    // =========================================================================

    @Test(priority = 8)
    public void test08_AlertOkCancelWithImpact() {
        System.out.println("TEST 08: Chấp nhận Alert và kiểm tra thông điệp");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickOkCancelDialogButton();
        String title = alertDialogsPage.getDialogTitle();
        Assert.assertTrue(title.contains("Lorem ipsum"), "Tiêu đề dialog không đúng!");
        alertDialogsPage.clickOkOnDialog();
        resetApp();
    }

    @Test(priority = 9)
    public void test09_AlertListSelectionDetailed() {
        System.out.println("TEST 09: Chọn nhiều item từ Alert List");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        String[] items = {"Command one", "Command two", "Command three"};
        for (String item : items) {
            System.out.println("Đang chọn item: " + item);
            alertDialogsPage.clickListDialogButton();
            alertDialogsPage.selectItemFromList(item);
            
            // Sau khi chọn item, một dialog thông báo hiện ra.
            // Sử dụng Back để đóng dialog này một cách an toàn và quay lại màn hình chính.
            sleep(1000);
            driver.navigate().back();
            sleep(500);
        }
        resetApp();
    }

    @Test(priority = 10)
    public void test10_AlertSingleChoice() {
        System.out.println("TEST 10: Alert Single Choice (Radio Buttons)");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickSingleChoiceDialogButton();
        
        System.out.println("Đang chọn 'Traffic'...");
        alertDialogsPage.selectItemFromList("Traffic");
        
        // Kiểm tra xem mục 'Traffic' đã được chọn (checked) chưa
        Assert.assertTrue(alertDialogsPage.isItemSelected("Traffic"), "LỖI: Mục 'Traffic' chưa được chọn!");
        
        System.out.println("Bấm OK để xác nhận...");
        alertDialogsPage.clickOkOnDialog();
        resetApp();
    }

    @Test(priority = 11)
    public void test11_AlertMultiChoice() {
        System.out.println("TEST 11: Alert Multi Choice (Checkboxes)");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickMultiChoiceDialogButton();
        
        System.out.println("Chọn 'Every Monday'");
        alertDialogsPage.selectItemFromList("Every Monday");
        Assert.assertTrue(alertDialogsPage.isItemSelected("Every Monday"), "LỖI: 'Every Monday' chưa được check!");
        
        System.out.println("Chọn 'Every Wednesday'");
        alertDialogsPage.selectItemFromList("Every Wednesday");
        Assert.assertTrue(alertDialogsPage.isItemSelected("Every Wednesday"), "LỖI: 'Every Wednesday' chưa được check!");

        alertDialogsPage.clickOkOnDialog();
        resetApp();
    }

    @Test(priority = 12)
    public void test12_AlertTextEntry() {
        System.out.println("TEST 12: Alert với Form nhập liệu");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickTextEntryDialogButton();
        alertDialogsPage.enterUsername("admin");
        alertDialogsPage.enterPassword("password123");
        alertDialogsPage.clickOkOnDialog();
        resetApp();
    }


    // =========================================================================
    // NHÓM 5: VIEWS & UNICODE (PRIORITY 15-17)
    // =========================================================================

    @Test(priority = 15)
    public void test15_ViewsScrolling() {
        System.out.println("TEST 15: Cuộn tìm và nhập liệu TextFields");
        homePage.clickViews();
        viewsPage.scrollToTextFields();
        viewsPage.clickTextFields();
        textFieldsPage.enterHelloAppium();
        sleep(800);
        resetApp();
    }

    @Test(priority = 16)
    public void test16_UnicodeVietnameseAndEmojis() {
        System.out.println("TEST 16: Kiểm tra tiếng Việt và dải Emoji");
        homePage.clickViews();
        controlsPage.clickControls();
        controlsPage.clickLightTheme();
        String complexText = "Xin chào 🇻🇳! 😊 🍕 ⚽";
        controlsPage.enterText(complexText);
        Assert.assertEquals(controlsPage.getEnteredText(), complexText, "Văn bản Unicode nhập vào không đúng!");
        sleep(1000);
        resetApp();
    }

    @Test(priority = 17)
    public void test17_ChronometerAllActions() {
        System.out.println("TEST 17: Chronometer - Kiểm tra toàn bộ 5 chức năng");
        homePage.clickViews();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Chronometer\"))")).click();
        
        // 1. Kiểm tra START
        System.out.println("-> Bấm START");
        chronometerPage.clickStart();
        sleep(3000);
        String timeAfterStart = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận sau 3s: " + timeAfterStart);
        Assert.assertNotEquals(timeAfterStart, "00:00", "LỖI: Đồng hồ không chạy!");

        // 2. Kiểm tra SET FORMAT
        System.out.println("-> Bấm SET FORMAT STRING");
        chronometerPage.clickSetFormat();
        sleep(2000);
        String timeFormatted = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận khi có Format: " + timeFormatted);
        Assert.assertTrue(timeFormatted.contains("Formatted time"), "LỖI: Định dạng không thay đổi!");

        // 3. Kiểm tra STOP
        System.out.println("-> Bấm STOP");
        chronometerPage.clickStop();
        String timeAtStop = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận lúc bấm Stop: " + timeAtStop);
        sleep(2000);
        String timeAfterWait = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận sau khi chờ 2s (đã stop): " + timeAfterWait);
        Assert.assertEquals(timeAtStop, timeAfterWait, "LỖI: Đồng hồ vẫn chạy sau khi dừng!");

        // 4. Kiểm tra CLEAR FORMAT
        System.out.println("-> Bấm CLEAR FORMAT STRING");
        chronometerPage.clickClearFormat();
        String timeCleared = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận sau khi xóa Format: " + timeCleared);
        Assert.assertFalse(timeCleared.contains("Formatted time"), "LỖI: Chữ 'Formatted time' vẫn còn!");

        // 5. Kiểm tra RESET
        System.out.println("-> Bấm RESET");
        chronometerPage.clickReset();
        sleep(1000);
        String timeReset = chronometerPage.getTimerText();
        System.out.println("Thời gian ghi nhận sau khi Reset: " + timeReset);
        Assert.assertTrue(timeReset.contains("00:00"), "LỖI: Đồng hồ không reset về 00:00!");

        resetApp();
    }

    // =========================================================================
    // HỖ TRỢ
    // =========================================================================

    private void resetApp() {
        driver.terminateApp("io.appium.android.apis");
        driver.activateApp("io.appium.android.apis");
        sleep(1500); // Đợi app load lại hoàn toàn
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
