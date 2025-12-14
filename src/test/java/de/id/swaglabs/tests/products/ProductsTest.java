package de.id.swaglabs.tests.products;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.pages.CartPage;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsTest extends BaseTest {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeEach
    void setup() {
        loginPage = new LoginPage(page);
        productsPage = new ProductsPage(page);
        cartPage = new CartPage(page);
        loginPage.open();
    }

    @Test
    void productShouldAddToCart() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        assertTrue(productsPage.isAt(), "Expected to be on product page");
        productsPage.addProductByIndex(0);
        String productName = productsPage.getInventoryItemNameByIndex(0);
        productsPage.goToShoppingCart();
        assertTrue(cartPage.isAt(), "Expected to be in shopping cart");
        assertEquals(1, cartPage.getCartItemCount());
        String cartItemname = cartPage.getCartItemNameByIndex(0);
        assertEquals(productName, cartItemname);
    }
}
