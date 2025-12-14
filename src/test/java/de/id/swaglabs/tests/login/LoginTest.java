package de.id.swaglabs.tests.login;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setup() {
        loginPage = new LoginPage(page);
        loginPage.open();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        ProductsPage productsPage = new ProductsPage(page);
        assertTrue(productsPage.isAt(), "Expected to be on inventory page after login");
    }

    @Test
    void shouldShowErrorOnInvalidLogin() {
        loginPage.login("wrongUser", "wrongPassword");
        String error = loginPage.getErrorMessage();
        System.out.println("Error: " + error);
        assertFalse(error.isEmpty(), "Error message should not be empty");
    }
}
