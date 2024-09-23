package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.NestedFramePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class NestedFrameTest {

    private final NestedFramePage nestedFramePage = new NestedFramePage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/nestedframes");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testParentFrame() {
        nestedFramePage.switchToParentFrame(); // Тест на родительский фрейм
    }

    @Test
    public void testChildFrame() {
        nestedFramePage.switchToChildFrame(); // Тест на вложенный фрейм
    }

    @Test
    public void testIframeCountInMainPage() {
        System.out.println("Number of iframes on the main page: " + nestedFramePage.getIFrameCountInMainPage());
    }

    @Test
    public void testIframeCountInParentFrame() {
        System.out.println("Number of iframes in the parent frame: " + nestedFramePage.getIFrameCountInParentFrame());
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
