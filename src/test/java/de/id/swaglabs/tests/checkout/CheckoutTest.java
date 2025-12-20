package de.id.swaglabs.tests.checkout;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.pages.CartPage;
import de.id.swaglabs.pages.CheckoutPage;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    void setup() {
        loginPage = new LoginPage(page);
        productsPage = new ProductsPage(page);
        cartPage = new CartPage(page);
        checkoutPage = new CheckoutPage(page);
        loginPage.open();
    }

    @Test
    void checkoutShouldContinueWithFilledForm() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        productsPage.waitUntilLoaded();
        productsPage.addProductByIndex(0);
        productsPage.goToShoppingCart();
        cartPage.waitUntilLoaded();
        assertEquals(1, cartPage.getCartBadgeCount());
        cartPage.goToCheckout();
        checkoutPage.waitUntilLoaded();
        checkoutPage.fillCustomerData("Max", "Mustermann", "11111");
        checkoutPage.continueCheckout();
    }

    @Test
    void checkoutShouldShowErrorWithMissingField() {
      loginPage.login(TestConfig.standardUser(), TestConfig.password());
      productsPage.waitUntilLoaded();
      productsPage.addProductByIndex(0);
      productsPage.goToShoppingCart();
      cartPage.waitUntilLoaded();
      cartPage.goToCheckout();
      checkoutPage.waitUntilLoaded();
      checkoutPage.fillCustomerData("Max", "Muster", "");
      checkoutPage.continueCheckout();
      assertTrue(checkoutPage.isErrorMsgVisible());
    }

    @Test
    void checkoutShouldCancelOnCancelButton() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        productsPage.waitUntilLoaded();
        productsPage.addProductByIndex(0);
        productsPage.goToShoppingCart();
        cartPage.waitUntilLoaded();
        cartPage.goToCheckout();
        checkoutPage.waitUntilLoaded();
        checkoutPage.cancelCheckout();
        cartPage.waitUntilLoaded();
    }
}
