package de.id.swaglabs.core;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    protected static BrowserContext context;
    protected static Page page;

    public static BrowserContext getContext() { return context; }
    public static Page getPage() { return page; }

    @BeforeEach
    void setupTest(){
        context = PlaywrightFactory.createContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
            context = null;
            page = null;
        }
    }
}
