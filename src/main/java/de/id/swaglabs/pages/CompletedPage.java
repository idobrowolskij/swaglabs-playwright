package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CompletedPage {
    private final Page page;
    private final Locator completedImg;
    private final Locator title;

    public CompletedPage(Page page) {
        this.page = page;
        this.completedImg = page.locator(".pony_express");
        this.title = page.locator(".title");
    }

    public void waitUntilLoaded() {
        page.waitForURL("**/checkout-complete.html");
        title.waitFor();
    }

    public boolean isCompletedImgVisible() {
        return completedImg.isVisible();
    }
}
