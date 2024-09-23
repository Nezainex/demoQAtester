package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.MenuPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MenuTest {

    MenuPage menuPage = new MenuPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/menu");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void clickMainItem1() {
        menuPage.getMainItem1().click();
    }

    @Test
    public void clickSubItem() {
        menuPage.hoverMainItem2();
        menuPage.getSubItem1().click();
    }

    @Test
    public void clickSubSubItem1() {
        menuPage.hoverMainItem2();
        menuPage.hoverSubSubList();
        menuPage.getSubSubItem1().click();
    }

    @Test
    public void clickSubSubItem2() {
        menuPage.hoverMainItem2();
        menuPage.hoverSubSubList();
        menuPage.getSubSubItem2().click();
    }

    @Test
    public void clickMainItem3() {
        menuPage.getMainItem3().click();
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
