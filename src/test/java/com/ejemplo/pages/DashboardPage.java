package com.ejemplo.pages;

import com.ejemplo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;

public class DashboardPage extends BasePage {
    
    @FindBy(how = How.ID, using = "welcomeMessage")
    private WebElement welcomeMessage;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isWelcomeMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
            return welcomeMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getWelcomeMessageText() {
        return welcomeMessage.getText();
    }
}
