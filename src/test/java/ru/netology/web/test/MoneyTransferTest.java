package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setUpPage() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        var loginPage = new LoginPage();
        var infoValidUser = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(infoValidUser);
        var verificationCode = DataHelper.getVerificationCodeFor(infoValidUser);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBefore = dashboardPage.getFirstCardBalance();
        var secondCardBefore = dashboardPage.getSecondCardBalance();
        int transfer = 5000;
        var replenishment = dashboardPage.replenishmentFirstCard();
        var numberCardFrom = DataHelper.getSecondCardNumberFor();
        var numberCardTo = DataHelper.getFirstCardNumberFor();
        var dashboardPageAfter = replenishment.replenishment(transfer, numberCardFrom, numberCardTo);
        var firstCardAfter = dashboardPageAfter.getFirstCardBalance();
        var secondCardAfter = dashboardPageAfter.getSecondCardBalance();
        assertEquals(firstCardBefore + transfer, firstCardAfter);
        assertEquals(secondCardBefore - transfer, secondCardAfter);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var loginPage = new LoginPage();
        var infoValidUser = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(infoValidUser);
        var verificationCode = DataHelper.getVerificationCodeFor(infoValidUser);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBefore = dashboardPage.getFirstCardBalance();
        var secondCardBefore = dashboardPage.getSecondCardBalance();
        int transfer = 5000;
        var replenishment = dashboardPage.replenishmentSecondCard();
        var numberCardFrom = DataHelper.getFirstCardNumberFor();
        var numberCardTo = DataHelper.getSecondCardNumberFor();
        var dashboardPageAfter = replenishment.replenishment(transfer, numberCardFrom, numberCardTo);
        var firstCardAfter = dashboardPageAfter.getFirstCardBalance();
        var secondCardAfter = dashboardPageAfter.getSecondCardBalance();
        assertEquals(firstCardBefore - transfer, firstCardAfter);
        assertEquals(secondCardBefore + transfer, secondCardAfter);
    }

    @Test
    void shouldTransferEmptyField() {
        var loginPage = new LoginPage();
        var infoValidUser = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(infoValidUser);
        var verificationCode = DataHelper.getVerificationCodeFor(infoValidUser);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var replenishment = dashboardPage.replenishmentFirstCard();
        replenishment.emptyField();
    }

    @Test
    void shouldTransferMoreThenLimitFromSecondToFirst() {
        var loginPage = new LoginPage();
        var infoValidUser = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(infoValidUser);
        var verificationCode = DataHelper.getVerificationCodeFor(infoValidUser);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBefore = dashboardPage.getFirstCardBalance();
        var secondCardBefore = dashboardPage.getSecondCardBalance();
        int transfer = 30000;
        var replenishment = dashboardPage.replenishmentFirstCard();
        var numberCardFrom = DataHelper.getSecondCardNumberFor();
        var numberCardTo = DataHelper.getFirstCardNumberFor();
        var dashboardPageAfter = replenishment.replenishment(transfer, numberCardFrom, numberCardTo);
        var firstCardAfter = dashboardPageAfter.getFirstCardBalance();
        var secondCardAfter = dashboardPageAfter.getSecondCardBalance();
        assertEquals(secondCardBefore, secondCardAfter);
        assertEquals(firstCardBefore, firstCardAfter);
    }
}