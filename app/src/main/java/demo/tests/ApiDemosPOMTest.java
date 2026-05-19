package demo.tests;

import demo.pages.AccessibilityPage;
import demo.pages.AnimationPage;
import demo.pages.ControlsPage;
import demo.pages.DefaultLayoutAnimationsPage;
import demo.pages.HideShowAnimationsPage;
import demo.pages.HomePage;
import demo.pages.TextFieldsPage;
import demo.pages.UnicodePage;
import demo.pages.ViewsPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiDemosPOMTest {
    private AndroidDriver driver;
    private HomePage homePage;
    private AccessibilityPage accessibilityPage;
    private AnimationPage animationPage;
    private DefaultLayoutAnimationsPage defaultLayoutAnimationsPage;
    private HideShowAnimationsPage hideShowAnimationsPage;
    private UnicodePage unicodePage;
    private ControlsPage controlsPage;
    private ViewsPage viewsPage;
    private TextFieldsPage textFieldsPage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2"); // Explicitly set correct case
        
        // Robust way to get the APK path whether running from root or app module
        File userDir = new File(System.getProperty("user.dir"));
        File projectRoot = userDir.getName().equals("app") ? userDir.getParentFile() : userDir;
        File appFile = new File(projectRoot, "app/build/outputs/apk/debug/app-debug.apk");
        
        if (!appFile.exists()) {
            // Fallback: search for any APK in the build folder if the standard path fails
            throw new RuntimeException("APK not found at: " + appFile.getAbsolutePath() + 
                    ". Please run ./gradlew assembleDebug first.");
        }

        options.setApp(appFile.getAbsolutePath());
        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity(".ApiDemos");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

        homePage = new HomePage(driver);
        accessibilityPage = new AccessibilityPage(driver);
        animationPage = new AnimationPage(driver);
        defaultLayoutAnimationsPage = new DefaultLayoutAnimationsPage(driver);
        hideShowAnimationsPage = new HideShowAnimationsPage(driver);
        unicodePage = new UnicodePage(driver);
        controlsPage = new ControlsPage(driver);
        viewsPage = new ViewsPage(driver);
        textFieldsPage = new TextFieldsPage(driver);
    }

    @Test
    public void testAccessibilityNodeProvider() {
        try {
            homePage.clickAccessibility();
            Assert.assertTrue(accessibilityPage.isAccessibilityNodeProviderVisible(), 
                    "Accessibility Node Provider should be visible");
            driver.navigate().back(); // Return to home
        } catch (Exception e) {
            System.out.println("Test failed! Current Page Source:");
            System.out.println(driver.getPageSource());
            throw e;
        }
    }

    @Test
    public void testDefaultLayoutAnimation() {
        homePage.clickAnimation();
        animationPage.clickDefaultLayoutAnimations();
        
        int initialCount = defaultLayoutAnimationsPage.getButtonsCount();
        int addClicks = 5;
        
        // Thêm 5 button
        for (int i = 0; i < addClicks; i++) {
            defaultLayoutAnimationsPage.clickAddButton();
            sleep(500); // Wait for add animation
        }
        
        int countAfterAdd = defaultLayoutAnimationsPage.getButtonsCount();
        Assert.assertEquals(countAfterAdd, initialCount + addClicks, "Số lượng button phải tăng thêm " + addClicks);
        
        // Xóa 3 button bằng cách click vào chúng
        int deleteClicks = 3;
        for (int j = 0; j < deleteClicks; j++) {
            // Luôn click vào button đầu tiên trong grid để xóa
            defaultLayoutAnimationsPage.clickButtonByIndex(0);
            sleep(500); // Wait for delete animation
        }
        
        int finalCount = defaultLayoutAnimationsPage.getButtonsCount();
        Assert.assertEquals(finalCount, countAfterAdd - deleteClicks, "Số lượng button phải giảm đi " + deleteClicks);
        
        driver.navigate().back(); // Back to Animation menu
        driver.navigate().back(); // Back to Home
    }

    @Test
    public void testHideShowAnimations() {
        homePage.clickAnimation();
        animationPage.clickHideShowAnimations();

        // 1. Trường hợp: Không có hiệu ứng (Mặc định - INVISIBLE)
        // Đảm bảo cả 2 checkbox đều tắt
        if (hideShowAnimationsPage.isCustomAnimationsChecked()) hideShowAnimationsPage.clickCustomAnimations();
        if (hideShowAnimationsPage.isHideGoneChecked()) hideShowAnimationsPage.clickHideGone();
        
        System.out.println("Testing: Default (Invisible)");
        hideShowAnimationsPage.clickButton("0");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("0"), "Nút 0 phải ẩn (Invisible)");
        hideShowAnimationsPage.clickShowButtons();
        sleep(1000);

        // 2. Trường hợp: Hiệu ứng thu nhỏ/gom lại (Hide GONE)
        hideShowAnimationsPage.clickHideGone();
        System.out.println("Testing: Hide (GONE) - Thu nhỏ layout");
        hideShowAnimationsPage.clickButton("0");
        sleep(1000);
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("0"), "Nút 0 phải biến mất hoàn toàn (GONE)");
        hideShowAnimationsPage.clickShowButtons();
        sleep(1000);

        // 3. Trường hợp: Hiệu ứng mờ dần & tùy chỉnh (Custom Animations)
        hideShowAnimationsPage.clickCustomAnimations();
        System.out.println("Testing: Custom Animations - Mờ dần & Đẹp mắt");
        hideShowAnimationsPage.clickButton("0");
        sleep(1000);
        // Lưu ý: isButtonVisible có thể trả về false ngay lập tức vì trạng thái Displayed thay đổi,
        // nhưng mắt người sẽ thấy animation chạy.
        Assert.assertFalse(hideShowAnimationsPage.isButtonVisible("0"), "Nút 0 phải ẩn với Custom Animation");
        hideShowAnimationsPage.clickShowButtons();
        sleep(1000);

        driver.navigate().back(); // Back to Animation menu
        driver.navigate().back(); // Back to Home
    }

    @Test
    public void testUnicodeDisplay() {
        homePage.clickText();
        unicodePage.clickUnicodeMenu();
        
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("عربي"), "Arabic text should be visible");
        Assert.assertTrue(unicodePage.isUnicodeTextVisible("தமிழ்"), "Tamil text should be visible");

        driver.navigate().back(); // Back to Text menu
        driver.navigate().back(); // Back to Home
    }

    @Test
    public void testUnicodeInput() {
        homePage.clickViews();
        controlsPage.clickControls();
        controlsPage.clickLightTheme();
        
        String unicodeText = "Số 1: Xin chào Việt Nam 🇻🇳";
        controlsPage.enterText(unicodeText);
        
        Assert.assertEquals(controlsPage.getEnteredText(), unicodeText, "Entered Unicode text should match");
        
        System.out.println("Input successful: " + unicodeText + ". Waiting 5 seconds...");
        sleep(5000); // Chờ 5 giây để xem kết quả
        
        driver.navigate().back(); // Back to Controls menu
        driver.navigate().back(); // Back to Views menu
        driver.navigate().back(); // Back to Home
    }

    @Test
    public void testViewsScrollingAndInput() {
        // 1. Click vào Views
        homePage.clickViews();
        sleep(2000);
        
        // 2. Scroll đến TextFields
        viewsPage.scrollToTextFields();
        
        // 3. Click vào TextFields
        viewsPage.clickTextFields();
        sleep(2000);
        
        // 4. Nhập text
        textFieldsPage.enterHelloAppium();
        sleep(2000);
        
        // 5. Quay lại
        driver.navigate().back();
        driver.navigate().back();
        
        // 6. Scroll tiếp (Scroll forward)
        viewsPage.scrollForward();
        sleep(2000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
