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

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    public void formSendSuccessWindowAppear(){
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dataDelivery = dateFormat.format(calendar.getTime());
        $("[data-test-id=date] input").val(dataDelivery);
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
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dataDelivery = dateFormat.format(calendar.getTime());
        $("[data-test-id=date] input").val(dataDelivery);
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
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dataDelivery = dateFormat.format(calendar.getTime());
        $("[data-test-id=date] input").val(dataDelivery);
        $("[data-test-id=name] input").val("Пушкин Алекандр");
        $("[data-test-id=phone] input").val("+79876543210");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification] .notification__title").should(appear, Duration.ofSeconds(15));
        String actual =  $("[data-test-id=notification] .notification__content").getText();
        String expected = "Встреча успешно забронирована на " + dataDelivery;
        assertEquals(expected, actual);
    }
}
