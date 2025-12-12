package de.id.swaglabs.core;

import com.microsoft.playwright.*;

public class PlayWrightFactory {
    private static Playwright playwright;
    private static Browser browser;

    public static Page createPage() {
        if (playwright == null) {
            playwright = Playwright.create();
        }

        if (browser == null) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions());
        }

        BrowserContext context = browser.newContext();
        return context.newPage();
    }

    public static void shutDown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }

        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
