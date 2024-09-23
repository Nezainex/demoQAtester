package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.BrokenLinksPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrokenLinksTest {

    private BrokenLinksPage brokenLinksPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/broken");
        getWebDriver().manage().window().maximize();
        brokenLinksPage = new BrokenLinksPage();
    }

    @Test
    public void testValidImage() {
        assertTrue(brokenLinksPage.isValidImageDisplayed(), "Valid image is not displayed");
    }

    @Test
    public void testBrokenImage() {
        assertTrue(brokenLinksPage.isBrokenImageDisplayed(), "Broken image is not displayed");
    }

    @Test
    public void testValidLink() {
        brokenLinksPage.clickValidLink();
    }

    @Test
    public void testBrokenLink() {
        brokenLinksPage.clickBrokenLink();
        // Проверяем, что после перехода на сломанную ссылку видим код ошибки 500
        assertTrue(getWebDriver().getPageSource().contains("500"), "Ожидался статус 500, но он не был найден");
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
