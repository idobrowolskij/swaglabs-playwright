package de.id.swaglabs.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.math.BigDecimal;

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

    public BigDecimal getPrice() {
       String text = root.locator(".inventory_item_price").innerText();
       return new BigDecimal(text.replace("$", "").trim());
    }

    public void addToCart() {
        root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Add to cart")).click();
    }

    public void remove() {
        root.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Remove")).click();
    }
}
