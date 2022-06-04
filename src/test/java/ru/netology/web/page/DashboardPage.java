package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item");
    private ElementsCollection buttons = $$("[data-test-id = 'action-deposit']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        $("[data-test-id = 'dashboard']").shouldBe(visible);
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.get(1).text();
        return extractBalance(text);
    }

    public ReplenishmentPage replenishmentFirstCard() {
        buttons.first().click();
        return new ReplenishmentPage();
    }

    public ReplenishmentPage replenishmentSecondCard() {
        buttons.get(1).click();
        return new ReplenishmentPage();
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}