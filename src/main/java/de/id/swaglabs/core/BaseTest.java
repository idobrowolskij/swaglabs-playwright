package de.id.swaglabs.core;

import com.microsoft.playwright.Page;
import de.id.swaglabs.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {
    protected static Page page;
    protected static LoginPage loginPage;

    @BeforeAll
    static void globalSetup() {
        page = PlayWrightFactory.createPage();
        loginPage = new LoginPage(page);
    }

    @AfterAll
    static void globalTeardown() {
        PlayWrightFactory.shutDown();
    }
}
