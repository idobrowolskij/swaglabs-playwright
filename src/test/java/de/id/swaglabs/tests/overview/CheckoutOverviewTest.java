package de.id.swaglabs.tests.overview;

import de.id.swaglabs.components.InventoryItem;
import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.pages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutOverviewTest extends BaseTest {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OverviewPage overviewPage;
    private CompletedPage completedPage;

    @BeforeEach
    void setup() {
        loginPage = new LoginPage(page);
        productsPage = new ProductsPage(page);
        cartPage = new CartPage(page);
        checkoutPage = new CheckoutPage(page);
        overviewPage = new OverviewPage(page);
        completedPage = new CompletedPage(page);
        loginPage.open();
    }

    @Test
    void overviewDataShouldMatchCart() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        productsPage.waitUntilLoaded();
        productsPage.addProductByIndex(0);
        productsPage.addProductByIndex(1);
        productsPage.addProductByIndex(2);
        InventoryItem item1 = productsPage.getInventoryItemByIndex(0);
        InventoryItem item2 = productsPage.getInventoryItemByIndex(1);
        InventoryItem item3 = productsPage.getInventoryItemByIndex(2);
        String item1Name = item1.getName();
        String item2Name = item2.getName();
        String item3Name = item3.getName();
        BigDecimal expectedTotal = item1.getPrice().add(item2.getPrice()).add(item3.getPrice());
        productsPage.goToShoppingCart();
        cartPage.waitUntilLoaded();
        assertEquals(item1Name, cartPage.getCartItemNameByIndex(0), "Product name should be the same in shopping cart");
        assertEquals(item2Name, cartPage.getCartItemNameByIndex(1), "Product name should be the same in shopping cart");
        assertEquals(item3Name, cartPage.getCartItemNameByIndex(2), "Product name should be the same in shopping cart");
        cartPage.goToCheckout();
        checkoutPage.waitUntilLoaded();
        checkoutPage.fillCustomerData("Max", "Muster", "11111");
        checkoutPage.continueCheckout();
        overviewPage.waitUntilLoaded();
        assertEquals(item1Name, overviewPage.getCartItemNameByIndex(0));
        assertEquals(item2Name, overviewPage.getCartItemNameByIndex(1));
        assertEquals(item3Name, overviewPage.getCartItemNameByIndex(2));
        assertEquals(expectedTotal, overviewPage.getItemTotal());
    }

    @Test
    void checkOutShouldCompleteWithValidData() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        productsPage.waitUntilLoaded();
        productsPage.addProductByIndex(0);
        productsPage.goToShoppingCart();
        cartPage.waitUntilLoaded();
        cartPage.goToCheckout();
        checkoutPage.waitUntilLoaded();
        checkoutPage.fillCustomerData("Max", "Muster", "11111");
        checkoutPage.continueCheckout();
        overviewPage.waitUntilLoaded();
        overviewPage.finishCheckout();
        completedPage.waitUntilLoaded();
        assertTrue(completedPage.isCompletedImgVisible());
    }
}
