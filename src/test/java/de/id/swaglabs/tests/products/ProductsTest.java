package de.id.swaglabs.tests.products;

import de.id.swaglabs.config.TestConfig;
import de.id.swaglabs.core.BaseTest;
import de.id.swaglabs.pages.CartPage;
import de.id.swaglabs.pages.LoginPage;
import de.id.swaglabs.pages.ProductsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void productShouldRemoveFromCart() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        assertTrue(productsPage.isAt(), "Expected to be on product page");
        productsPage.addProductByIndex(0);
        productsPage.waitForCartBadgeCount(1);
        assertEquals(1, productsPage.getShoppingCartBadgeCount());
        productsPage.getInventoryItemByIndex(0).remove();
        productsPage.waitForCartBadgeCount(0);
        assertEquals(0, productsPage.getShoppingCartBadgeCount());
    }

    @Test
    void productsShouldSortFromZtoA() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        assertTrue(productsPage.isAt());
        productsPage.selectZToASorting();
        List<String> actual = productsPage.getAllProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        assertEquals(expected, actual, "Products should be sorted Z to A");
    }

    @Test
    void productsShouldSortFromLowToHigh() {
        loginPage.login(TestConfig.standardUser(), TestConfig.password());
        assertTrue(productsPage.isAt());
        productsPage.selectLowToHighSorting();
        List<BigDecimal> actual = productsPage.getAllProductPrices();
        List<BigDecimal> expected = new ArrayList<>(actual);
        //sort((a,b) -> a.compareTo(b)
        expected.sort(BigDecimal::compareTo);
        assertEquals(expected, actual, "Products should be sorted from low to high");
    }
}
