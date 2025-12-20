package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import de.id.swaglabs.components.CartItem;

import java.math.BigDecimal;

public class OverviewPage {
    private final Page page;
    private final Locator finishButton;
    private final Locator itemTotalLabel;
    private final Locator cartItems;
    private final Locator title;

    public OverviewPage(Page page) {
        this.page = page;
        this.finishButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Finish"));
        this.itemTotalLabel = page.locator(".summary_subtotal_label");
        this.cartItems = page.locator(".cart_item");
        this.title = page.locator(".title");
    }

    public void waitUntilLoaded() {
        page.waitForURL("**/checkout-step-two.html");
        title.waitFor();
    }

    public void finishCheckout() {
        finishButton.click();
    }

    public BigDecimal getItemTotal() {
        String s = itemTotalLabel.innerText();
        String numeric = s.replaceAll("[^0-9.]", "");
        return new BigDecimal(numeric);
    }

    public String getCartItemNameByIndex(int index) {
        CartItem item = new CartItem(cartItems.nth(index));
        return item.getItemName();
    }
}
