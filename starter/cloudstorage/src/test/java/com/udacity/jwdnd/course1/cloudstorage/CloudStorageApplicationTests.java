package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
//		loginPage.login("admin", "admin");	// ¡ESTO FUNCIONA, HABILITAR!
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getSignupPage() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
//        Thread.sleep(10000);
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void getHomePage() {
        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    public void getResultPage() {
        driver.get("http://localhost:" + this.port + "/result");
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Result", driver.getTitle());
    }
}
