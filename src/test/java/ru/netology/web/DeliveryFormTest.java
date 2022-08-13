package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DeliveryFormTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldShowValidFormCompletion() {
        $("span[data-test-id='city'] input").setValue("Кр");
        $$("div.popup__content div").find(exactText("Краснодар")).click();
        $("span[data-test-id='date'] button").click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        SimpleDateFormat rusDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateMeeting = rusDateFormat.format(calendar.getTime());
        SelenideElement dateElement = $("[data-test-id=date] input[class=input__control]");
        dateElement.sendKeys("\b\b\b\b\b\b\b\b\b\b");
        dateElement.setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
}

