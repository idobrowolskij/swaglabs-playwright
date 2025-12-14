package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.id.swaglabs.components.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    private final Page page;
    private final Locator cartItems;
    private final Locator title;

    public CartPage(Page page) {
        this.page = page;
        this.cartItems = page.locator(".cart_item");
        this.title = page.locator(".title");
    }

    public boolean isAt() {
        return page.url().contains("cart.html") && title.isVisible();
    }

    private void waitUntilLoaded() {
        page.waitForURL("**/cart.html");
        title.waitFor();
    }

    private void waitUntilHasItems() {
        waitUntilLoaded();
        cartItems.first().waitFor();
    }

    public List<CartItem> getParsedCartItems() {
        waitUntilHasItems();
        List<CartItem> items = new ArrayList<>();
        for (int i = 0; i < cartItems.count(); i++) {
            items.add(new CartItem(cartItems.nth(i)));
        }
        return items;
    }

    public int getCartItemCount() {
        waitUntilLoaded();
        return cartItems.count();
    }

    public String getCartItemNameByIndex(int index) {
        waitUntilHasItems();
        CartItem item = new CartItem(cartItems.nth(index));
        return item.getItemName();
    }
}
