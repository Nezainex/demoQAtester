package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DatePickerPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DatePickerTest {

    private final DatePickerPage datePickerPage = new DatePickerPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/date-picker");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testSetDateByTextInFirstPicker() {
        datePickerPage.setDateByTextInFirstPicker("08/08/2023");
    }

    @Test
    public void testSelectDateInFirstPicker() {
        datePickerPage.selectDateInFirstPicker("August", "2023");
    }

    @Test
    public void testSelectTimeInSecondPicker() {
        datePickerPage.selectTimeInSecondPicker("03:00");
    }

    @Test
    public void testDecreaseDateAndTimeInSecondPicker() {
        datePickerPage.decreaseDateAndTimeInSecondPicker();
    }

    @Test
    public void testIncreaseDateByOneMonthInSecondPicker() {
        datePickerPage.increaseDateByOneMonthInSecondPicker();
    }

    @Test
    public void testSetDateByTextInSecondPicker() {
        datePickerPage.setDateByTextInSecondPicker("09/10/2024");
    }

    @Test
    public void testSelectDateInSecondPicker() {
        datePickerPage.selectDateInSecondPicker("September", "2024");
    }

    @AfterEach
    public void resetPage() {
        open("https://demoqa.com/date-picker");
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
