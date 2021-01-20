package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    private static EncryptionService encryptionService;

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String username = "TestUser";
    private String password = "veryBadPassword012345";
    private String firstname = "Jacinto";
    private String lastname = "Alvarez";

    private String noteTitle = "1st note";
    private String noteDescription = "This is the first note";

    private String noteTitleChanged = noteTitle + "Changed";
    private String noteDescriptionChanged = noteDescription + "Changed";

    private String credentialURL = "https://www.test.com";
    private String credentialUsername = "testuser";
    private String credentialPassword = "passw";

    private String credentialURLChanged = "https://www.test.com/changed";
    private String credentialUsernameChanged = "testuserchanged";
    private String credentialPasswordChanged = "passwchanged";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        encryptionService = new EncryptionService();
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

    /*** Test signup and login flow ***/

    // Write a Selenium test that verifies that the home page is not accessible without logging in.
    @Test
    @Order(1)
    public void HomePageNotAccessibleWithoutLoggingIn() {
        driver.get("http://localhost:" + this.port + "/home");

        Assertions.assertNotEquals("home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }

    //    Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
    //    then logs out and verifies that the home page is no longer accessible.
    @Test
    @Order(2)
    public void SingUpNewUserAndLogUserAndUserCanAccessHomePageAndLogOutAndHomePageNoLongerAccessible() {

        // Sing up a new user
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstname, lastname, username, password);
        Assertions.assertEquals("You successfully signed up! Please login to continue.", signupPage.getSuccessMessage());
        Assertions.assertEquals("Login", driver.getTitle());


        // Log the user in
        logTheUserIn();


        // verify that they can access the home page
        Assertions.assertEquals("Home", driver.getTitle());


        // log out and verify that the home page is no longer accessible
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickLogOut();
        Assertions.assertNotEquals("Home", driver.getTitle());
        Assertions.assertEquals("Login", driver.getTitle());
    }


    /*** Test adding, editing, and deleting notes ***/

    // Selenium test that logs in an existing user, creates a note and verifies that the note details are visible in the note list
    @Test
    @Order(3)
    public void loginExistingUserAndCreateANoteAndVerifyNoteDetailsAreVisibleInNoteList() {

        // Log the user in
        logTheUserIn();


        // create a note
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickNoteTab();
        homePage.clickAddNoteButton();
        homePage.fillNote(noteTitle, noteDescription);
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your note was created successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the note details are visible in the note list
        Assertions.assertEquals(noteTitle, homePage.getNoteTitleOnList());
        Assertions.assertEquals(noteDescription, homePage.getNoteDescriptionOnList());
    }

    // Selenium test that logs in an existing user with existing notes, clicks the edit note button on an existing note,
    // changes the note data, saves the changes, and verifies that the changes appear in the note list.
    @Test
    @Order(4)
    public void loginExistingUserWithExistingNotesAndEditNoteAndChangeNoteAndSaveChangesAndVerifyNoteDetailsAreVisibleInNoteList() {

        // Log the user in
        logTheUserIn();


        // edit an existing note
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickNoteTab();
        homePage.clickEditNoteButton();
        homePage.fillNote(noteTitleChanged, noteDescriptionChanged);
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your note was updated successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the note details are visible in the note list
        Assertions.assertEquals(noteTitleChanged, homePage.getNoteTitleOnList());
        Assertions.assertEquals(noteDescriptionChanged, homePage.getNoteDescriptionOnList());
    }

    // Selenium test that logs in an existing user with existing notes, clicks the delete note button on an existing
    // note, and verifies that the note no longer appears in the note list.
    @Test
    @Order(5)
    public void loginExistingUserWithExistingNotesAndDeleteNoteAndVerifyNoteDetailsAreVisibleInNoteList() {

        // Log the user in
        logTheUserIn();


        // delete an existing note
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickNoteTab();
        homePage.clickNoteLink();
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your note was deleted successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the note no longer appears in the note list
        Assertions.assertFalse(homePage.isElementPresent(By.id("note-title-on-list")));
        Assertions.assertFalse(homePage.isElementPresent(By.id("note-description-on-list")));
    }

    /*** Test adding, editing and deleting credentials ***/

    // Selenium test that logs in an existing user, creates a credential and verifies that the credential details are visible in the credential list.
    @Test
    @Order(6)
    public void loginExistingUserAndCreateACredentialAndVerifyCredentialDetailsAreVisibleInCredentialList() {

        // Log the user in
        logTheUserIn();


        // create a credential
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialTab();
        ;
        homePage.clickAddCredentialButton();
        homePage.fillCredential(credentialURL, credentialUsername, credentialPassword);
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your credential was created successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the note details are visible in the note list
        Assertions.assertEquals(credentialURL, homePage.getCredentialURLOnList());
        Assertions.assertEquals(credentialUsername, homePage.getCredentialUsernameOnList());
//        Assertions.assertEquals(credentialPassword, encryptionService.decryptValue(homePage.getCredentialPasswordOnList(), homePage.getCredentialKeyOnList()));
    }

    // Selenium test that logs in an existing user with existing credentials, clicks the edit credential button on
    // an existing credential, changes the credential data, saves the changes, and verifies that the changes appear in the credential list.
    @Test
    @Order(7)
    public void loginExistingUserWithExistingCredentialsAndEditCredentialAndChangeCredentialAndSaveChangesAndVerifyCredentialDetailsAreVisibleInCredentialList() {

        // Log the user in
        logTheUserIn();


        // edit an existing credential
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialTab();
        homePage.clickEditCredentialButton();
        homePage.fillCredential(credentialURLChanged, credentialUsernameChanged, credentialPasswordChanged);
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your credential was updated successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the credential details are visible in the credential list
        Assertions.assertEquals(credentialURLChanged, homePage.getCredentialURLOnList());
        Assertions.assertEquals(credentialUsernameChanged, homePage.getCredentialUsernameOnList());
//        Assertions.assertEquals(credentialPasswordChanged, encryptionService.decryptValue(homePage.getCredentialPasswordOnList(), homePage.getCredentialKeyOnList()));
    }


    // Selenium test that logs in an existing user with existing credentials, clicks the delete credential button on an
    // existing credential, and verifies that the credential no longer appears in the credential list.
    @Test
    @Order(8)
    public void loginExistingUserWithExistingCredentialsAndDeleteCredentialAndVerifyCredentialDetailsAreVisibleInCredentialList() {

        // Log the user in
        logTheUserIn();


        // delete an existing credential
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialTab();
        homePage.clickCredentialLink();
        Assertions.assertEquals("Result", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertEquals("Your credential was deleted successfully.", resultPage.getSuccessMessage());
        resultPage.goToHomeLinkFromSuccess();
        Assertions.assertEquals("Home", driver.getTitle());


        // verify that the note no longer appears in the note list
        Assertions.assertFalse(homePage.isElementPresent(By.id("credential-url-on-list")));
        Assertions.assertFalse(homePage.isElementPresent(By.id("credential-username-on-list")));
        Assertions.assertFalse(homePage.isElementPresent(By.id("credential-password-on-list")));
    }


    private void logTheUserIn() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Assertions.assertNotEquals("Login", driver.getTitle());
        Assertions.assertEquals("Home", driver.getTitle());
    }
}
