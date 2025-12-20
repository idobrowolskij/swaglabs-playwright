package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import de.id.swaglabs.components.CartItem;

public class CartPage {

    private final Page page;
    private final Locator cartItems;
    private final Locator title;
    private final Locator shoppingCartBadge;
    private final Locator checkoutButton;

    public CartPage(Page page) {
        this.page = page;
        this.cartItems = page.locator(".cart_item");
        this.title = page.locator(".title");
        this.shoppingCartBadge = page.locator(".shopping_cart_badge");
        this.checkoutButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Checkout"));
    }

    public void waitUntilLoaded() {
        page.waitForURL("**/cart.html");
        title.waitFor();
    }

    public int getCartItemCount() {
        return cartItems.count();
    }

    public String getCartItemNameByIndex(int index) {
        CartItem item = new CartItem(cartItems.nth(index));
        return item.getItemName();
    }

    public void removeFromCartByIndex(int index) {
        cartItems.nth(index).getByText("Remove").click();
    }

    public int getCartBadgeCount() {
        return shoppingCartBadge.count() > 0 ? Integer.parseInt(shoppingCartBadge.innerText().trim()) : 0;
    }

    public void goToCheckout() {
        checkoutButton.click();
    }
}
