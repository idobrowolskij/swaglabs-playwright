package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.id.swaglabs.components.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    private final Page page;
    private final Locator title;
    private final Locator inventoryItems;
    private final Locator shoppingCartLink;

    public ProductsPage(Page page) {
        this.page = page;
        this.title = page.locator(".title");
        this.inventoryItems = page.locator(".inventory_item");
        this.shoppingCartLink = page.locator(".shopping_cart_link");
    }

    public boolean isAt() {
        return page.url().contains("inventory.html") && title.isVisible();
    }

    private void waitUntilLoaded() {
        page.waitForURL("**/inventory.html");
        title.waitFor();
    }

    private void waitUntilHasItems() {
        waitUntilLoaded();
        inventoryItems.first().waitFor();
    }

    public void addProductByIndex(int index) {
        waitUntilHasItems();
        inventoryItems.nth(index).locator("button[data-test^='add-to-cart']").click();
    }

    public void goToShoppingCart() {
        shoppingCartLink.click();
    }

    public List<InventoryItem> getParsedItems() {
        waitUntilLoaded();
        List<InventoryItem> items = new ArrayList<>();
        for (int i = 0; i < inventoryItems.count(); i++) {
            items.add(new InventoryItem(inventoryItems.nth(i)));
        }
        return items;
    }

    public String getInventoryItemNameByIndex(int index) {
        waitUntilHasItems();
        InventoryItem item = new InventoryItem(inventoryItems.nth(index));
        return item.getName();
    }

}
