package de.id.swaglabs.core;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void globalSetup() {
        PlaywrightFactory.getBrowser();
    }

    @BeforeEach
    void setupTest(){
        context = PlaywrightFactory.createContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @AfterAll
    static void globalTeardown() {
        PlaywrightFactory.shutdown();
    }
}
