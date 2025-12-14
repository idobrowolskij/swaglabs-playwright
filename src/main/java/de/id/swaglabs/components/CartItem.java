package de.id.swaglabs.components;

import com.microsoft.playwright.Locator;

public class CartItem {
    private final Locator root;

    public CartItem(Locator root) {
        this.root = root;
    }

    public String getItemName() {
        return root.locator(".inventory_item_name").innerText();
    }

    public String getItemDescription() {
        return root.locator(".inventory_item_desc").innerText();
    }

    public String getItemPrice() {
        return root.locator(".inventory_item_price").innerText();
    }

    public String getItemQuantity() {
        return root.locator(".cart_quantity").innerText();
    }
}
