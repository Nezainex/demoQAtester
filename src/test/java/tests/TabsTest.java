package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.TabsPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TabsTest {

    private final TabsPage tabsPage = new TabsPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/tabs");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testTabWhat() {
        tabsPage.clickTabWhat();
        assertTrue(tabsPage.isWhatTabActive(), "Вкладка 'What' не активна.");
        assertTrue(tabsPage.isWhatPanelVisible(), "Панель вкладки 'What' не видна.");
    }

    @Test
    public void testTabOrigin() {
        tabsPage.clickTabOrigin();
        assertTrue(tabsPage.isOriginTabActive(), "Вкладка 'Origin' не активна.");
        assertTrue(tabsPage.isOriginPanelVisible(), "Панель вкладки 'Origin' не видна.");
    }

    @Test
    public void testTabUse() {
        tabsPage.clickTabUse();
        assertTrue(tabsPage.isUseTabActive(), "Вкладка 'Use' не активна.");
        assertTrue(tabsPage.isUsePanelVisible(), "Панель вкладки 'Use' не видна.");
    }

    @Test
    public void testTabMore() {
        // Проверяем, что вкладка 'More' недоступна (aria-disabled="true")
        assertTrue(tabsPage.isTabMoreDisabled(), "Вкладка 'More' должна быть недоступна (disabled), но она доступна.");
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
