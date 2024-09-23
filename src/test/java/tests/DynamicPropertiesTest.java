package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DynamicPropertiesPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DynamicPropertiesTest {

    private DynamicPropertiesPage dynamicPropertiesPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;// Открытие нового браузера для каждого теста
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/dynamic-properties");
        getWebDriver().manage().window().maximize();
        dynamicPropertiesPage = new DynamicPropertiesPage();
    }

    @Test
    public void testEnableButton() {
        dynamicPropertiesPage.waitForEnableButton();
    }

    @Test
    public void testColorChange() {
        dynamicPropertiesPage.verifyColorChange();
    }

    @Test
    public void testVisibleAfterButton() {
        dynamicPropertiesPage.waitForVisibleButton();
    }

    @Test
    public void testRandomText() {
        dynamicPropertiesPage.verifyRandomText();
    }

    @AfterEach
    public void tearDown() {
        try {
            // Закрываем все окна, если они существуют
            for (String handle : getWebDriver().getWindowHandles()) {
                getWebDriver().switchTo().window(handle).close();
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при закрытии окон: " + e.getMessage());
        } finally {
            try {
                // Закрываем WebDriver, если сессия активна
                getWebDriver().quit();
            } catch (Exception e) {
                System.out.println("Ошибка при завершении сессии WebDriver: " + e.getMessage());
            }
        }
    }
}
