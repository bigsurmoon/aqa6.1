package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {
    private SelenideElement amount = $("[data-test-id = 'amount'] input");
    private SelenideElement from = $("[data-test-id = 'from'] input");
    private SelenideElement to = $("[data-test-id = 'to'] input");
    private SelenideElement replenishmentButton = $("[data-test-id = 'action-transfer']");

    public ReplenishmentPage() {
        amount.shouldBe(visible);
    }

    private String lastFourNumbers(String text) {
        val value = text.substring(15);
        return value;
    }

    public DashboardPage replenishment(int transfer, DataHelper.CardInfo numberFrom, DataHelper.CardInfo numberTo) {
        amount.setValue(String.valueOf(transfer));
        from.setValue(numberFrom.getNumber());
        to.shouldHave(attributeMatching("value", ".*" + lastFourNumbers(numberTo.getNumber())));
        replenishmentButton.click();
        return new DashboardPage();
    }

    public DashboardPage emptyField() {
        replenishmentButton.click();
        $("[data-test-id= 'error-notification']").shouldBe(visible).shouldHave(text("Ошибка"));
        return new DashboardPage();
    }
}