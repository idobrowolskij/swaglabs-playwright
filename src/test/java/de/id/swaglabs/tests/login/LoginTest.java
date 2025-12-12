package de.id.swaglabs.tests.login;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    @BeforeEach
    void setup() {
        loginPage.open();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
    }

    @Test
    void shouldShowErrorOnInvalidLogin() {
        loginPage.login("wrongUser", "wrongPassword");
        String error = loginPage.getErrorMessage();
        System.out.println("Error: " + error);
        assertFalse(error.isEmpty(), "Error message should not be empty");
    }
}
