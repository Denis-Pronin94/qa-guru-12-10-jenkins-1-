package com.demoqa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("demoqa")
public class Student_Registration_Form extends TestBase {

    String firstName = "Denis";
    String lastName = "Pronin";
    String userEmail = "Denis_Pronin@yandex.ru";
    String userNumber = "8956456565";
    String currentAddress = "House 123";

    @Test
    @DisplayName("Successful fill registration form test")
    void fillFormTest() {
        step("Open registration form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });

        step("Fill registration form", () -> {
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(userEmail);
            $(".custom-control-label").click();
            $("#userNumber").setValue(userNumber);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("December");
            $(".react-datepicker__year-select").selectOption("1994");
            $("[aria-label='Choose Thursday, December 8th, 1994']").click();
            $("#subjectsInput").click();
            $("#subjectsInput").setValue("M").pressEnter();
            $("#hobbiesWrapper").find(byText("Sports")).click();
            $("#uploadPicture").uploadFromClasspath("img/460.jpg");
            $("#currentAddress").setValue(currentAddress);
            $("#state").click();
            $(byText("Haryana")).click();
            $("#city").click();
            $(byText("Karnal")).click();
            $("#submit").pressEnter();
        });

        step("Verify form data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text(firstName + " " + lastName),
                    text(userEmail), text("Male"), text(userNumber),
                    text("08 December,1994"), text("Maths"), text("Sports"), text("460.jpg"),
                    text(currentAddress), text("Haryana Karnal"));
        });
    }
}