package de.id.swaglabs.tests.cart;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.core.PlaywrightArtifactsExtension;
import de.id.swaglabs.pages.CartPage;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PlaywrightArtifactsExtension.class)
public class CartTest extends BaseTest {

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
    void cartShouldBeEmptyAfterRemove() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        productsPage.waitUntilLoaded();
        productsPage.addProductByIndex(0);
        productsPage.goToShoppingCart();
        cartPage.waitUntilLoaded();
        assertEquals(1, cartPage.getCartBadgeCount());
        cartPage.removeFromCartByIndex(0);
        assertEquals(0, cartPage.getCartBadgeCount());
    }
}
