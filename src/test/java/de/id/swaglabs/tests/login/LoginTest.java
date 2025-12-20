package de.id.swaglabs.tests.login;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.core.PlaywrightArtifactsExtension;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PlaywrightArtifactsExtension.class)
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeEach
    void setup() {
        loginPage = new LoginPage(page);
        productsPage = new ProductsPage(page);
        loginPage.open();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        assertTrue(loginPage.getErrorMessage().isEmpty());
        productsPage.waitUntilLoaded();
    }

    @Test
    void shouldShowErrorOnInvalidLogin() {
        loginPage.login("wrongUser", "wrongPassword");
        String error = loginPage.getErrorMessage();
        assertFalse(error.isEmpty(), "Error message should not be empty");
    }
}
