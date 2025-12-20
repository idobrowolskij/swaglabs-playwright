package de.id.swaglabs.core;

import com.microsoft.playwright.*;

public class PlaywrightFactory {
    private static Playwright playwright;
    private static Browser browser;
    private static boolean shutdownHookRegistered = false;

    public static Browser getBrowser() {
        if (playwright == null) {
            playwright = Playwright.create();
        }

        if (browser == null) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        }

        if (!shutdownHookRegistered) {
            shutdownHookRegistered = true;
            Runtime.getRuntime().addShutdownHook(new Thread(PlaywrightFactory::shutdown));
        }

        return browser;
    }

    public static BrowserContext createContext() {
        return getBrowser().newContext();
    }

    public static void shutdown() {
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
