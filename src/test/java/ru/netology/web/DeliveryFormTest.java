package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

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

    String dateMeeting = generateDate(10);

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldShowDateCalculation() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateMeeting), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldShowCityIsNotEntered() {
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowNoDateHasBeenEntered() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    void shouldShowNameIsNotEntered() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowPhoneIsNotEntered() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowCheckboxIsNotEntered() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(By.className("button")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldShowIncorrectCity() {
        $("span[data-test-id='city'] input").setValue("Павлвовск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowIncorrectDate() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("15.08.2022");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldShowIncorrectName() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='name'] .input__sub")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowIncorrectPhone() {
        $("span[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'] .input__sub")
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSelectInTheCalendar() {
        $("span[data-test-id='city'] input").setValue("Кр");
        $$("div.popup__content div").find(exactText("Краснодар")).click();
        $("span[data-test-id='date'] button").click();
        $("[data-test-id=date] input").click();
        int days = 4;
        for (int cycle = 0; cycle < days; cycle++) {
            $(".calendar").sendKeys(Keys.ARROW_RIGHT);
        }
        $(".calendar").sendKeys(Keys.ENTER);
        String dateTest = $("[data-test-id=date] input").getValue();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateTest), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}


