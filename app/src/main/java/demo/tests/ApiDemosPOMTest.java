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
 * sử dụng mô hình Page Object Model (POM).
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

        // Khởi tạo tất cả các đối tượng Page
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

    // --- ACCESSIBILITY ---

    @Test
    public void test1_AccessibilityNodeProvider() {
        System.out.println("TEST CASE 1: Kiểm tra hiển thị Accessibility Node Provider");
        homePage.clickAccessibility();
        Assert.assertTrue(accessibilityPage.isAccessibilityNodeProviderVisible());
        driver.navigate().back();
    }

    // --- ANIMATION ---

    @Test
    public void test2_DefaultLayoutAdd() {
        System.out.println("TEST CASE 2: Thêm button trong Default Layout");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        int initial = defaultLayoutAnimationsPage.getButtonsCount();
        defaultLayoutAnimationsPage.clickAddButton();
        sleep(500);
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), initial + 1);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test3_DefaultLayoutRemove() {
        System.out.println("TEST CASE 3: Xóa button trong Default Layout");
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

    @Test
    public void test4_HideShowInvisible() {
        System.out.println("TEST CASE 4: Ẩn button chế độ Invisible");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test5_HideShowGone() {
        System.out.println("TEST CASE 5: Ẩn button chế độ GONE (layout co lại)");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (!hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        hideShowAnimationsPage.clickButton("1");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("1"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test6_HideShowCustom() {
        System.out.println("TEST CASE 6: Hiệu ứng ẩn Custom");
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();
        if (!hideShowAnimationsPage.isCustomAnimationsChecked()) hideShowAnimationsPage.clickCustomAnimations();
        hideShowAnimationsPage.clickButton("2");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("2"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test10_DeleteAllButtons() {
        System.out.println("TEST CASE 10: Xóa sạch toàn bộ button");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        for(int i=0; i<3; i++) defaultLayoutAnimationsPage.clickAddButton();
        defaultLayoutAnimationsPage.clearAllButtons();
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), 0);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test11_AddDeleteFast() {
        System.out.println("TEST CASE 11: Thêm và xóa nhanh");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        defaultLayoutAnimationsPage.clickAddButton();
        defaultLayoutAnimationsPage.clickButtonByIndex(0);
        sleep(500);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test12_RemoveReverseOrder() {
        System.out.println("TEST CASE 12: Xóa ngược từ cuối lên");
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        for(int i=0; i<3; i++) defaultLayoutAnimationsPage.clickAddButton();
        int count = defaultLayoutAnimationsPage.getButtonsCount();
        for(int i=count-1; i>=0; i--) {
            defaultLayoutAnimationsPage.clickButtonByIndex(i);
            sleep(200);
        }
        Assert.assertEquals(defaultLayoutAnimationsPage.getButtonsCount(), 0);
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test13_BouncingBalls() {
        System.out.println("TEST CASE 13: Tạo bóng nảy");
        homePage.clickAnimation();
        animationPage.clickBouncingBalls();
        bouncingBallsPage.tapOnScreen(500, 500);
        bouncingBallsPage.tapOnScreen(200, 800);
        sleep(2000);
        driver.navigate().back(); driver.navigate().back();
    }

    // --- TEXT ---

    @Test
    public void test7_UnicodeArabic() {
        System.out.println("TEST CASE 7: Hiển thị tiếng Ả Rập");
        homePage.clickText();
        unicodePage.clickUnicodeMenu();
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("عربي"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test8_UnicodeTamil() {
        System.out.println("TEST CASE 8: Hiển thị tiếng Tamil");
        homePage.clickText();
        unicodePage.clickUnicodeMenu();
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("தமிழ்"));
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test9_UnicodeVietnamese() {
        System.out.println("TEST CASE 9: Nhập tiếng Việt và Emoji");
        homePage.clickViews();
        controlsPage.clickControls();
        controlsPage.clickLightTheme();
        String text = "Số 1: Xin chào 🇻🇳";
        controlsPage.enterText(text);
        Assert.assertEquals(controlsPage.getEnteredText(), text);
        sleep(2000);
        driver.navigate().back(); driver.navigate().back(); driver.navigate().back();
    }

    // --- VIEWS ---

    @Test
    public void test14_ViewsScrolling() {
        System.out.println("TEST CASE 14: Cuộn tìm TextFields");
        homePage.clickViews();
        viewsPage.scrollToTextFields();
        viewsPage.clickTextFields();
        textFieldsPage.enterHelloAppium();
        sleep(1000);
        driver.navigate().back(); driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test19_Chronometer() {
        System.out.println("TEST CASE 19: Đồng hồ bấm giờ");
        homePage.clickViews();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Chronometer\"))")).click();
        chronometerPage.clickStart();
        sleep(2000);
        String time = chronometerPage.getTimerText();
        System.out.println("Thời gian: " + time);
        chronometerPage.clickStop();
        driver.navigate().back(); driver.navigate().back();
    }

    // --- APP / ALERTS ---

    @Test
    public void test15_AlertOk() {
        System.out.println("TEST CASE 15: Chấp nhận Alert");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickOkCancelDialogButton();
        alertDialogsPage.clickOkOnDialog();
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test16_AlertListSelection() {
        System.out.println("TEST CASE 16: Chọn item từ Alert List");
        homePage.clickApp();
        appPage.clickAlertDialogs();
        alertDialogsPage.clickListDialogButton();
        alertDialogsPage.selectItemFromList("Command one");
        sleep(1000);
        driver.navigate().back(); driver.navigate().back();
    }

    // --- PREFERENCE ---

    @Test
    public void test17_PreferenceWiFi() {
        System.out.println("TEST CASE 17: Bật/Tắt WiFi Preference");
        homePage.clickPreference();
        preferencePage.clickPreferenceDependencies();
        preferencePage.clickWiFiCheckbox();
        driver.navigate().back(); driver.navigate().back();
    }

    @Test
    public void test18_PreferenceDependency() {
        System.out.println("TEST CASE 18: Kiểm tra phụ thuộc WiFi Settings");
        homePage.clickPreference();
        preferencePage.clickPreferenceDependencies();
        // Nếu checkbox tắt, WiFi settings phải bị disable
        if (preferencePage.isWiFiSettingsEnabled()) preferencePage.clickWiFiCheckbox();
        Assert.assertFalse(preferencePage.isWiFiSettingsEnabled());
        preferencePage.clickWiFiCheckbox(); // Bật lại
        Assert.assertTrue(preferencePage.isWiFiSettingsEnabled());
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
