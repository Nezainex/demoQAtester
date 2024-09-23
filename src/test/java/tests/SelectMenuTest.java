package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.SelectMenuPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SelectMenuTest {

    SelectMenuPage selectMenuPage = new SelectMenuPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/select-menu");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testSelectValue() {
        selectMenuPage.selectOptionByText(selectMenuPage.getSelectValue(), "Group 1, option 1");
    }

    @Test
    public void testSelectOne() {
        selectMenuPage.selectOptionByText(selectMenuPage.getSelectOne(), "Mr.");
    }

    @Test
    public void testOldStyleSelectMenu() {
        selectMenuPage.getOldStyleSelectMenu().selectOptionContainingText("Green");
    }

    @Test
    public void testMultiselectDropdown() {
        selectMenuPage.selectOptionByText(selectMenuPage.getMultiselectDropdown(), "Green");
        selectMenuPage.selectOptionByText(selectMenuPage.getMultiselectDropdown(), "Blue");  // Можно выбрать несколько значений
    }

    @Test
    public void testStandardMultiSelect() {
        selectMenuPage.getStandardMultiSelect().selectOptionByValue("volvo");
        selectMenuPage.getStandardMultiSelect().selectOptionByValue("audi");
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
