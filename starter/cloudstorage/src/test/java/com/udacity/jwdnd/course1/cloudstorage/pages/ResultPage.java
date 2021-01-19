package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id = "successMessageText")
    private WebElement successMessage;

    @FindBy(id = "goToHomeLinkFromSuccess")
    private WebElement homeLinkFromSuccess;

    @FindBy(id = "goToHomeLinkFromError")
    private WebElement homeLinkFromError;

    private final JavascriptExecutor js;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        js = (JavascriptExecutor) webDriver;
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public void goToHomeLinkFromSuccess() {
        js.executeScript("arguments[0].click();", homeLinkFromSuccess);
    }

    public void goToHomeLinkFromError() {
        js.executeScript("arguments[0].click();", homeLinkFromError);
    }
}
