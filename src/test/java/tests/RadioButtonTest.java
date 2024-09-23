package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.RadioButtonPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RadioButtonTest {

    private final RadioButtonPage radioButtonPage = new RadioButtonPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/radio-button");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testSelectYes() {
        radioButtonPage.selectYes();
        String result = radioButtonPage.getSelectedResult();
        assertEquals(result, "You have selected Yes", "Ожидается результат: 'You have selected Yes'");
    }

    @Test
    public void testSelectImpressive() {
        radioButtonPage.selectImpressive();
        String result = radioButtonPage.getSelectedResult();
        assertEquals(result, "You have selected Impressive", "Ожидается результат: 'You have selected Impressive'");
    }

    @Test
    public void testNoDisabled() {
        boolean isDisabled = radioButtonPage.isNoDisabled();  // Проверяем состояние кнопки
        assertTrue(isDisabled, "Кнопка 'No' должна быть отключена.");
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
