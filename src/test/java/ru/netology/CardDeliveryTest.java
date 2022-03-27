package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


public class CardDeliveryTest {

    public String dataDelivery() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dataDelivery = dateFormat.format(calendar.getTime());
        return dataDelivery;
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    public void formSendSuccessWindowAppear(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").should(appear, Duration.ofSeconds(15));
    }

    @Test
    public void formSendSuccessMessageSuccessfully(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").should(appear, Duration.ofSeconds(15));
        String actual =  $("[data-test-id=notification] .notification__title").getText();
        String expected = "Успешно!";
        assertEquals(expected, actual);
    }

    @Test
    public void formSendSuccessMessageDateDelivery(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").should(appear, Duration.ofSeconds(15));
        String actual =  $("[data-test-id=notification] .notification__content").getText();
        String expected = "Встреча успешно забронирована на " + dataDelivery();
        assertEquals(expected, actual);
    }

    @Test
    public void allNull(){
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void cityNull(){
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void cityInvalid(){
        $("[data-test-id=city] input").val("Обнинск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }

    @Test
    public void dateNull(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Неверно введена дата";
        assertEquals(expected, actual);
    }

    @Test
    public void dateIsOutdated(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("01.01.2021");
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Заказ на выбранную дату невозможен";
        assertEquals(expected, actual);
    }

    @Test
    public void dateInvalid(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("32.04.2022");
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Неверно введена дата";
        assertEquals(expected, actual);
    }

    @Test
    public void nameNull(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=name].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void nameInvalid(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр-2");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=name].input_invalid .input__sub").getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void phoneNull() {
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void phoneInvalid() {
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("89876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsNullCheckboxChecked(){
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsValidCheckboxUnchecked(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(dataDelivery());
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $(withText("Забронировать")).click();
        boolean actual = $("[data-test-id=agreement].input_invalid").isDisplayed();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsInvalidCheckboxChecked(){
        $("[data-test-id=city] input").val("Обнинск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("32.04.2022");
        $("[data-test-id=name] input").val("Пушкин Алекандр-2");
        $("[data-test-id=phone] input").val("89876543210");
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsInvalidCheckboxUnchecked(){
        $("[data-test-id=city] input").val("Обнинск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("32.04.2022");
        $("[data-test-id=name] input").val("Пушкин Алекандр-2");
        $("[data-test-id=phone] input").val("89876543210");
        $(withText("Забронировать")).click();
        String actual =  $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }
}
