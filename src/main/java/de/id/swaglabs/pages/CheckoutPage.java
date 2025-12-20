package de.id.swaglabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckoutPage {
    private final Page page;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator zipInput;
    private final Locator continueButton;
    private final Locator cancelButton;
    private final Locator title;
    private final Locator errorMsg;

    public CheckoutPage(Page page) {
        this.page = page;
        this.firstNameInput = page.getByPlaceholder("First Name");
        this.lastNameInput = page.getByPlaceholder("Last Name");
        this.zipInput = page.getByPlaceholder("Zip/Postal Code");
        this.continueButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
        this.cancelButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
        this.title = page.locator(".title");
        this.errorMsg = page.locator("[data-test='error']");
    }

    public void waitUntilLoaded() {
        page.waitForURL("**/checkout-step-one.html");
        title.waitFor();
    }

    public void continueCheckout() {
        continueButton.click();
    }

    public void cancelCheckout() {
        cancelButton.click();
    }

    public void fillCustomerData(String firstName, String lastName, String zip) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        zipInput.fill(zip);
    }

    public boolean isErrorMsgVisible() {
        return errorMsg.isVisible();
    }

}
