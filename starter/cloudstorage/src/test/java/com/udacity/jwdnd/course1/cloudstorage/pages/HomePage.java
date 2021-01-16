package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(className="btn btn-secondary float-right")
    private WebElement submitLogoutButton;

    @FindBy(id="nav-files")
    private WebElement navFilesDiv;

    @FindBy(id="nav-notes")
    private WebElement navNotesDiv;

    @FindBy(id="nav-credentials")
    private WebElement navCredentialsDiv;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    // TODO: METHODS
}
