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
 * Lớp ApiDemosPOMTest thực hiện 19 bài kiểm thử tự động (Test Cases)
 * được sắp xếp theo trình tự số thứ tự tăng dần từ 1 đến 19.
 */
public class ApiDemosPOMTest {
    private AndroidDriver driver;
    private HomePage homePage;
    private AccessibilityPage accessibilityPage;
    private AnimationPage animationPage;
    private BouncingBallsPage bouncingBallsPage;
    private DefaultLayoutAnimationsPage defaultLayoutAnimationsPage;
    private HideShowAnimationsPage hideShowAnimationsPage;
    private UnicodePage unicodePage;
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
        unicodePage = new UnicodePage(driver);
        controlsPage = new ControlsPage(driver);
        viewsPage = new ViewsPage(driver);
        textFieldsPage = new TextFieldsPage(driver);
        appPage = new AppPage(driver);
        alertDialogsPage = new AlertDialogsPage(driver);
        preferencePage = new PreferencePage(driver);
        chronometerPage = new ChronometerPage(driver);
    }

    // =========================================================================
    // DANH SÁCH 19 TEST CASES (SẮP XẾP THEO THỨ TỰ 1-19)
    // =========================================================================

    @Test(priority = 1)
    public void test1_AccessibilityNodeProvider() {
        System.out.println("TEST 1: Kiểm tra Accessibility Node Provider");
        homePage.clickAccessibility();
        Assert.assertTrue(accessibilityPage.isAccessibilityNodeProviderVisible());
        driver.navigate().back();
    }

    @Test(priority = 2)
    public void test2_DefaultLayoutAdd() {
        System.out.println("TEST 2: Thêm button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        int initial = defaultLayoutAnimationsPage.getButtonsCount();
        defaultLayoutAnimationsPage.clickAddButton();
        sleep(500);
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), initial + 1);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 3)
    public void test3_DefaultLayoutRemove() {
        System.out.println("TEST 3: Xóa button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        defaultLayoutAnimationsPage.clickAddButton();
        sleep(500);
        int count = defaultLayoutAnimationsPage.getButtonsCount();
        defaultLayoutAnimationsPage.clickButtonByIndex(0);
        sleep(500);
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), count - 1);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 4)
    public void test4_HideShowInvisible() {
        System.out.println("TEST 4: Ẩn button chế độ Invisible");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 5)
    public void test5_HideShowGone() {
        System.out.println("TEST 5: Ẩn button chế độ GONE");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (!hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 6)
    public void test6_HideShowCustom() {
        System.out.println("TEST 6: Hiệu ứng ẩn Custom");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (!hideShowAnimationsPage.isCustomAnimationsChecked()) hideShowAnimationsPage.clickCustomAnimations();
        hideShowAnimationsPage.clickButton("2");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("2"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 7)
    public void test7_UnicodeArabic() {
        System.out.println("TEST 7: Hiển thị tiếng Ả Rập");
        homePage.clickText();
        unicodePage.clickUnicodeMenu();
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("عربي"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 8)
    public void test8_UnicodeTamil() {
        System.out.println("TEST 8: Hiển thị tiếng Tamil");
        homePage.clickText();
        unicodePage.clickUnicodeMenu();
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("தமிழ்"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 9)
    public void test9_UnicodeVietnamese() {
        System.out.println("TEST 9: Nhập tiếng Việt và Emoji");
        homePage.clickViews();
        controlsPage.clickControls();
        controlsPage.clickLightTheme();
        String text = "Số 1: Xin chào 🇻🇳";
        controlsPage.enterText(text);
        Assert.assertEquals(controlsPage.getEnteredText(), text);
        sleep(2000);
        driver.navigate().back(); driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 10)
    public void test10_DeleteAllButtons() {
        System.out.println("TEST 10: Xóa sạch toàn bộ button");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        for(int i=0; i<3; i++) defaultLayoutAnimationsPage.clickAddButton();
        defaultLayoutAnimationsPage.clearAllButtons();
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), 0);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 11)
    public void test11_AddDeleteFast() {
        System.out.println("TEST 11: Thêm và xóa nhanh");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        defaultLayoutAnimationsPage.clickAddButton();
        defaultLayoutAnimationsPage.clickButtonByIndex(0);
        sleep(500);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 12)
    public void test12_RemoveReverseOrder() {
        System.out.println("TEST 12: Xóa ngược từ cuối lên");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        for(int k=0; k<3; k++) defaultLayoutAnimationsPage.clickAddButton();
        int count = defaultLayoutAnimationsPage.getButtonsCount();
        for(int m=count-1; m>=0; m--) {
            defaultLayoutAnimationsPage.clickButtonByIndex(m);
            sleep(200);
        }
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), 0);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 13)
    public void test13_BouncingBalls() {
        System.out.println("TEST 13: Tạo bóng nảy");
        homePage.clickAnimation();
        animationPage.clickBouncingBalls();
        bouncingBallsPage.tapOnScreen(500, 500);
        bouncingBallsPage.tapOnScreen(200, 800);
        sleep(2000);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 14)
    public void test14_ViewsScrolling() {
        System.out.println("TEST 14: Cuộn tìm TextFields");
        homePage.clickViews();
        viewsPage.scrollToTextFields();
        viewsPage.clickTextFields();
        textFieldsPage.enterHelloAppium();
        sleep(1000);
        driver.navigate().back(); driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 15)
    public void test15_AlertOk() {
        System.out.println("TEST 15: Chấp nhận Alert");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickOkCancelDialogButton();
        alertDialogsPage.clickOkOnDialog();
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 16)
    public void test16_AlertListSelection() {
        System.out.println("TEST 16: Chọn item từ Alert List");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickListDialogButton();
        alertDialogsPage.selectItemFromList("Command one");
        sleep(1000);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 17)
    public void test17_PreferenceWiFi() {
        System.out.println("TEST 17: Bật/Tắt WiFi Preference");
        homePage.clickPreference();
        preferencePage.clickPreferenceDependencies();
        preferencePage.clickWiFiCheckbox();
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 18)
    public void test18_PreferenceDependency() {
        System.out.println("TEST 18: Kiểm tra phụ thuộc WiFi Settings");
        homePage.clickPreference();
        preferencePage.clickPreferenceDependencies();
        if (preferencePage.isWiFiSettingsEnabled()) preferencePage.clickWiFiCheckbox();
        Assert.assertFalse(preferencePage.isWiFiSettingsEnabled());
        preferencePage.clickWiFiCheckbox(); 
        Assert.assertTrue(preferencePage.isWiFiSettingsEnabled());
        driver.navigate().back(); driver.navigate().back();
    }

    @Test(priority = 19)
    public void test19_Chronometer() {
        System.out.println("TEST 19: Đồng hồ bấm giờ");
        homePage.clickViews();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Chronometer\"))")).click();
        chronometerPage.clickStart();
        sleep(2000);
        String time = chronometerPage.getTimerText();
        System.out.println("Thời gian: " + time);
        chronometerPage.clickStop();
        driver.navigate().back(); driver.navigate().back();
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
