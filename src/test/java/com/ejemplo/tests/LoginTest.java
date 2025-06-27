package com.ejemplo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ejemplo.base.BaseTest;
import com.ejemplo.pages.LoginPage;

public class LoginTest extends BaseTest {
    

    @Test
    @Tag("smoke")
    @Tag("positive")
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(getBaseUrl());
        loginPage.waitForPageLoad();

        loginPage.login(getUsername(), getPassword());

        boolean isWelcomeDisplayed = loginPage.isWelcomeMessageDisplayed();
        String welcomeText = loginPage.getWelcomeMessageText();

        Assertions.assertTrue(isWelcomeDisplayed, "El mensaje de bienvenida si es visible.");
        Assertions.assertEquals("You logged into a secure area!", welcomeText, "La comparación es correcta");
    }

    @Test
    @Tag("regression")
    @Tag("negative")
    void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo(getBaseUrl());
        loginPage.waitForPageLoad();

        String invalidUsername = "user";
        String invalidPassword = "pass";

        loginPage.login(invalidUsername, invalidPassword);

        boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed();
        String errorText = loginPage.getErrorMessageText();

        Assertions.assertTrue(isErrorDisplayed, "El mensaje de error existe.");
        Assertions.assertEquals("Your username is invalid!", errorText, "El mensaje del error es el esperado.");
    }
}
