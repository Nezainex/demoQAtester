package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.SelectablePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectableTest {

    private SelectablePage selectablePage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/selectable");
        getWebDriver().manage().window().maximize();
        selectablePage = new SelectablePage();
    }

    @Test
    public void testListSelectable() {
        selectablePage.getListTab().click();

        // Выбор первого элемента
        selectablePage.selectItem(selectablePage.getListItems(), 0);
        assertTrue(selectablePage.isItemSelected(selectablePage.getListItems(), 0), "Первый элемент должен быть выбран.");

        // Снятие выбора
        selectablePage.selectItem(selectablePage.getListItems(), 0);
        assertFalse(selectablePage.isItemSelected(selectablePage.getListItems(), 0), "Первый элемент должен быть снят с выбора.");
    }

    @Test
    public void testGridSelectable() {
        selectablePage.getGridTab().click();

        // Выбор третьего элемента
        selectablePage.selectItem(selectablePage.getGridItems(), 2);
        assertTrue(selectablePage.isItemSelected(selectablePage.getGridItems(), 2), "Третий элемент должен быть выбран.");

        // Снятие выбора
        selectablePage.selectItem(selectablePage.getGridItems(), 2);
        assertFalse(selectablePage.isItemSelected(selectablePage.getGridItems(), 2), "Третий элемент должен быть снят с выбора.");
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
