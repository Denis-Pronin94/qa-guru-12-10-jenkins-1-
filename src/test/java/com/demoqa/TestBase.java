package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {

    static String url = System.getProperty("baseDemoUrl");
    static String browserSize = System.getProperty("browserSize");
    static String selenoidUrl = System.getProperty("selenoidServer");

    @BeforeAll
    static void setUp() {

        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = url;
        Configuration.browserSize = browserSize;
        Configuration.remote = "https://" + config.login() + ":" + config.password() + "@" + selenoidUrl;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}