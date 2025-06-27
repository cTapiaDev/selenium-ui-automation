package com.ejemplo.pages;

import com.ejemplo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException; 

public class LoginPage extends BasePage {

    @FindBy(how = How.ID, using = "username")
    private WebElement usernameInput;

    @FindBy(how = How.ID, using = "password")
    private WebElement passwordInput;

    @FindBy(how = How.CSS, using = "#login button[type='submit']") 
    private WebElement loginButton;

    @FindBy(how = How.ID, using = "flash") 
    private WebElement flashMessage; 
    

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        usernameInput.clear(); 
        usernameInput.sendKeys(username);

        passwordInput.clear(); 
        passwordInput.sendKeys(password);

        loginButton.click();
        
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            throw new RuntimeException("Sleep interrupted", e);
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(flashMessage, "invalid!")); 
            return flashMessage.isDisplayed(); 
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        wait.until(ExpectedConditions.textToBePresentInElement(flashMessage, "invalid!")); 
        return flashMessage.getText()
                           .replace("\n", " ")  
                           .replace("×", "")   
                           .trim();             
    }

    public boolean isWelcomeMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(flashMessage, "You logged into a secure area!")); 
            return flashMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getWelcomeMessageText() {
        wait.until(ExpectedConditions.textToBePresentInElement(flashMessage, "You logged into a secure area!"));
        return flashMessage.getText()
                           .replace("\n", " ") 
                           .replace("×", "")  
                           .trim();             
    }
}
