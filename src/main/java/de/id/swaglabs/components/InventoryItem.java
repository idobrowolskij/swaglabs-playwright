package de.id.swaglabs.components;

import com.microsoft.playwright.Locator;

public class InventoryItem {
    private final Locator root;

    public InventoryItem(Locator root) {
        this.root = root;
    }

    public String getName() {
        return root.locator(".inventory_item_name").innerText();
    }

    public String getDescription() {
        return root.locator(".inventory_item_desc").innerText();
    }

    public String getPrice() {
        return root.locator(".inventory_item_price").innerText();
    }

    public void addToCart() {
        root.locator("button").click();
    }
}
