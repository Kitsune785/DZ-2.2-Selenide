package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DeliveryFormTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String dateMeeting = generateDate(7);

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldShowDirectInput(){
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("22.08.2022");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=notification]")
                .$(withText("Встреча успешно забронирована"))
                .shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldShowDateCalculation() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(7));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateMeeting), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldShowValidFormCompletion() {
        $("span[data-test-id='city'] input").setValue("Кр");
        $$("div.popup__content div").find(exactText("Краснодар")).click();
        $("span[data-test-id='date'] button").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);



        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateMeeting), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}


