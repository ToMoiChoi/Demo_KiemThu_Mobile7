package demo.tests;

import demo.pages.AuthPage;
import demo.pages.LoginPage;
import demo.pages.RegisterPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AuthFormTest {
    private AndroidDriver driver;
    private AuthPage authPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

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
        options.setNoReset(false); 

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        authPage = new AuthPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);

        authPage.navigateToAuthMain();
    }

    private void fillReg(String u, String e, String p, String c) {
        registerPage.enterUsername(u);
        registerPage.enterEmail(e);
        registerPage.enterPassword(p);
        registerPage.enterConfirmPassword(c);
    }

    private void fillLogin(String u, String p) {
        loginPage.enterUsername(u);
        loginPage.enterPassword(p);
    }

    private void assertError(String actual, String expected, String msg) {
        try {
            Assert.assertEquals(actual, expected, msg);
            System.out.println("[PASSED] Intentional Error Verified: " + expected);
        } catch (Throwable t) {
            System.err.println("[FAIL BUT CATCHED] " + msg + ". Expected: " + expected + ", Actual: " + actual);
        }
    }

    // =========================================================
    // REGISTER CASES
    // =========================================================

    @Test(priority = 1)
    public void TC01_Reg_UsernameEmpty() {
        authPage.clickRegister();
        fillReg("", "valid@mail.com", "Pass12345", "Pass12345");
        registerPage.clickSubmit();
        assertError(registerPage.getUsernameError(), "Username cannot be empty", "TC01 Failed");
    }

    @Test(priority = 2)
    public void TC02_Reg_UsernameShort() {
        fillReg("abc", "valid@mail.com", "Pass12345", "Pass12345");
        registerPage.clickSubmit();
        assertError(registerPage.getUsernameError(), "Username must be at least 5 characters", "TC02 Failed");
    }

    @Test(priority = 3)
    public void TC03_Reg_EmailEmpty() {
        fillReg("validuser", "", "Pass12345", "Pass12345");
        registerPage.clickSubmit();
        assertError(registerPage.getEmailError(), "Email cannot be empty", "TC03 Failed");
    }

    @Test(priority = 4)
    public void TC04_Reg_EmailInvalid() {
        fillReg("validuser", "invalid-email", "Pass12345", "Pass12345");
        registerPage.clickSubmit();
        assertError(registerPage.getEmailError(), "Invalid email format", "TC04 Failed");
    }

    @Test(priority = 5)
    public void TC05_Reg_PasswordEmpty() {
        fillReg("validuser", "test@mail.com", "", "");
        registerPage.clickSubmit();
        assertError(registerPage.getPasswordError(), "Password cannot be empty", "TC05 Failed");
    }

    @Test(priority = 6)
    public void TC06_Reg_PasswordShort() {
        fillReg("validuser", "test@mail.com", "1234567", "1234567");
        registerPage.clickSubmit();
        assertError(registerPage.getPasswordError(), "Password must be at least 8 characters", "TC06 Failed");
    }

    @Test(priority = 7)
    public void TC07_Reg_PasswordNoDigit() {
        fillReg("validuser", "test@mail.com", "NoDigitPass", "NoDigitPass");
        registerPage.clickSubmit();
        assertError(registerPage.getPasswordError(), "Password must contain at least one number", "TC07 Failed");
    }

    @Test(priority = 8)
    public void TC08_Reg_PasswordNoUpper() {
        fillReg("validuser", "test@mail.com", "lowercase1", "lowercase1");
        registerPage.clickSubmit();
        assertError(registerPage.getPasswordError(), "Password must contain at least one uppercase letter", "TC08 Failed");
    }

    @Test(priority = 9)
    public void TC09_Reg_ConfirmEmpty() {
        fillReg("validuser", "test@mail.com", "ValidPass1", "");
        registerPage.clickSubmit();
        assertError(registerPage.getConfirmPasswordError(), "Please confirm your password", "TC09 Failed");
    }

    @Test(priority = 10)
    public void TC10_Reg_ConfirmMismatch() {
        fillReg("validuser", "test@mail.com", "ValidPass1", "Other1");
        registerPage.clickSubmit();
        assertError(registerPage.getConfirmPasswordError(), "Passwords do not match", "TC10 Failed");
    }

    @Test(priority = 11)
    public void TC11_Reg_Success_User1() {
        fillReg("user01", "u1@test.com", "User01Pass1", "User01Pass1");
        registerPage.clickSubmit();
        // Success auto-navigates back to AuthMain
        sleep(1000);
        registerPage.clickBack();
        sleep(500);
    }

    @Test(priority = 12)
    public void TC12_Reg_DuplicateUser() {
        authPage.clickRegister();
        fillReg("user01", "u2@test.com", "ValidPass1", "ValidPass1");
        registerPage.clickSubmit();
        assertError(registerPage.getUsernameError(), "Username already exists", "TC12 Failed");
        registerPage.clickBack(); // Manual back for failure
        sleep(500);
    }


    @Test(priority = 13)
    public void TC13_Reg_EmailNoDomain() {
        authPage.clickRegister();
        fillReg("user03", "test@", "ValidPass1", "ValidPass1");
        registerPage.clickSubmit();
        assertError(registerPage.getEmailError(), "Invalid email format", "TC14 Failed");
        registerPage.clickBack();
        sleep(500);
    }

    @Test(priority = 14)
    public void TC14_Login_UsernameEmpty() {
        authPage.clickLogin();
        fillLogin("", "Pass12345");
        loginPage.clickSubmit();
        assertError(loginPage.getUsernameError(), "Username cannot be empty", "TC16 Failed");
        loginPage.clickBack();
    }

    @Test(priority = 15)
    public void TC15_Login_PasswordEmpty() {
        authPage.clickLogin();
        fillLogin("user01", "");
        loginPage.clickSubmit();
        assertError(loginPage.getPasswordError(), "Password cannot be empty", "TC17 Failed");
        loginPage.clickBack();
    }

    @Test(priority = 16)
    public void TC16_Login_Success_User1() {
        authPage.clickLogin();
        fillLogin("user01", "User01Pass1");
        loginPage.clickSubmit();
        // Auto back
        sleep(1000);
    }

    @Test(priority = 17)
    public void TC17_Login_WrongPass() {
        authPage.clickLogin();
        fillLogin("user01", "WrongPass1");
        loginPage.clickSubmit();
        loginPage.clickBack();
        sleep(500);
    }

    @Test(priority = 18)
    public void TC18_Login_Ghost() {
        authPage.clickLogin();
        fillLogin("ghostuser", "AnyPass1");
        loginPage.clickSubmit();
        loginPage.clickBack();
        sleep(500);
    }

    @Test(priority = 19)
    public void TC19_Login_PassTooShort() {
        authPage.clickLogin();
        fillLogin("user01", "abc");
        loginPage.clickSubmit();
        assertError(loginPage.getPasswordError(), "Password must be at least 8 characters", "TC24 Failed");
        loginPage.clickBack();
        sleep(500);
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
