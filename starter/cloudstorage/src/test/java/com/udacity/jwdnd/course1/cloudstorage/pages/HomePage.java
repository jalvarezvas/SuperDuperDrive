package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logout;


    /*** Notes ***/

//    @FindBy(xpath = "//a[@id='nav-notes-tab']")
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-title-on-list")
    private WebElement noteTitleOnList;

    @FindBy(id = "note-description-on-list")
    private WebElement noteDescriptionOnList;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "submitNoteButton")
    private WebElement submitNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteLink")
    private WebElement deleteNoteLink;


    /*** Credentials ***/

//    @FindBy(xpath = "//a[@id='nav-credentials-tab']")
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "credential-url")
    private WebElement credentialURL;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-key")
    private WebElement credentialKey;

    @FindBy(id = "credential-url-on-list")
    private WebElement credentialURLOnList;

    @FindBy(id = "credential-username-on-list")
    private WebElement credentialUsernameOnList;

    @FindBy(id = "credential-password-on-list")
    private WebElement credentialPasswordOnList;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "submitCredentialButton")
    private WebElement submitCredentialButton;

    @FindBy(id = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredentialLink")
    private WebElement deleteCredentialLink;


    private final WebDriver driver;
    private final JavascriptExecutor js;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
        driver = webDriver;
    }

    public void clickLogOut() {
        js.executeScript("arguments[0].click();", logout);
    }

    public boolean isElementPresent(By by) {

        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /*** Notes ***/

    public void clickNoteTab() {
        js.executeScript("arguments[0].click();", notesTab);
    }

    public void clickAddNoteButton() {
        js.executeScript("arguments[0].click();", addNoteButton);
    }

    public void clickEditNoteButton() {
        js.executeScript("arguments[0].click();", editNoteButton);
    }

    public void clickNoteLink() {
        js.executeScript("arguments[0].click();", deleteNoteLink);
    }

    public void fillNote(String noteTitle, String noteDescription) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitle);
        js.executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescription);

        js.executeScript("arguments[0].click();", submitNoteButton);
    }


    public String getNoteTitleOnList() {
        return noteTitleOnList.getAttribute("innerHTML");
    }

    public String getNoteDescriptionOnList() {
        return noteDescriptionOnList.getAttribute("innerHTML");
    }

    /*** Credentials ***/

    public void clickCredentialTab() {
        js.executeScript("arguments[0].click();", credentialsTab);
    }

    public void clickAddCredentialButton() {
        js.executeScript("arguments[0].click();", addCredentialButton);
    }

    public void clickEditCredentialButton() {
        js.executeScript("arguments[0].click();", editCredentialButton);
    }

    public void clickCredentialLink() {
        js.executeScript("arguments[0].click();", deleteCredentialLink);
    }

    public void fillCredential(String credentialURL, String credentialUsername, String credentialPassword) {
        js.executeScript("arguments[0].value='" + credentialURL + "';", this.credentialURL);
        js.executeScript("arguments[0].value='" + credentialUsername + "';", this.credentialUsername);
        js.executeScript("arguments[0].value='" + credentialPassword + "';", this.credentialPassword);

        js.executeScript("arguments[0].click();", submitCredentialButton);
    }

    public String getCredentialURLOnList() {
        return credentialURLOnList.getAttribute("innerHTML");
    }

    public String getCredentialUsernameOnList() {
        return credentialUsernameOnList.getAttribute("innerHTML");
    }

    public String getCredentialPasswordOnList() {
        return credentialPasswordOnList.getAttribute("innerHTML");
    }

}