package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(className="alert alert-success fill-parent")
    private WebElement successDiv;

    @FindBy(className="alert alert-danger fill-parent")
    private WebElement errorDiv;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    // TODO: METHODS
}
