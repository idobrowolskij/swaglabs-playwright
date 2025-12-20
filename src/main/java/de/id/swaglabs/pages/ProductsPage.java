package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import de.id.swaglabs.components.InventoryItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    private final Page page;
    private final Locator title;
    private final Locator inventoryItems;
    private final Locator shoppingCartLink;
    private final Locator shoppingCartBadge;
    private final Locator productSortContainer;

    public ProductsPage(Page page) {
        this.page = page;
        this.title = page.locator(".title");
        this.inventoryItems = page.locator(".inventory_item");
        this.shoppingCartLink = page.locator(".shopping_cart_link");
        this.shoppingCartBadge = page.locator(".shopping_cart_badge");
        this.productSortContainer = page.locator(".product_sort_container");
    }

    public void waitUntilLoaded() {
        page.waitForURL("**/inventory.html");
        title.waitFor();
    }

    public void addProductByIndex(int index) {
        inventoryItems.nth(index).locator("button[data-test^='add-to-cart']").click();
    }

    public void goToShoppingCart() {
        shoppingCartLink.click();
    }

    public String getInventoryItemNameByIndex(int index) {
        InventoryItem item = new InventoryItem(inventoryItems.nth(index));
        return item.getName();
    }

    public InventoryItem getInventoryItemByIndex(int index) {
        return new InventoryItem(inventoryItems.nth(index));
    }

    public int getShoppingCartBadgeCount() {
        return shoppingCartBadge.count() > 0 ? Integer.parseInt(shoppingCartBadge.innerText()) : 0;
    }

    public void waitForCartBadgeCount(int expected) {
        page.waitForCondition(() -> getShoppingCartBadgeCount() == expected);
    }

    public void selectZToASorting() {
        productSortContainer.selectOption("Name (Z to A)");
    }

    public void selectLowToHighSorting() {
        productSortContainer.selectOption("Price (low to high)");
    }

    public List<String> getAllProductNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < inventoryItems.count(); i++) {
            names.add(new InventoryItem(inventoryItems.nth(i)).getName().trim());
        }
        return names;
    }

    public List<BigDecimal> getAllProductPrices() {
        List<BigDecimal> prices = new ArrayList<>();
        for (int i = 0; i < inventoryItems.count(); i++) {
            BigDecimal price = new InventoryItem(inventoryItems.nth(i))
                    .getPrice();
            prices.add(price);
        }
        return prices;
    }

}
