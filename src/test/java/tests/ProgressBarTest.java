package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import pages.ProgressBarPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProgressBarTest {

    private final ProgressBarPage progressBarPage = new ProgressBarPage();

    @BeforeAll
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/progress-bar");
        getWebDriver().manage().window().maximize();
    }

    @Test
    @Order(1)
    public void testProgressBarComplete() {
        progressBarPage.clickStartStopButton();  // Запускаем прогресс-бар
        progressBarPage.waitForProgressBarToComplete();  // Ждём, пока прогресс не дойдёт до 100%
    }

    @Test
    @Order(2)
    public void testResetProgressBar() {
        progressBarPage.waitForProgressBarToComplete();  // Ждём, пока прогресс не дойдёт до 100%
        progressBarPage.clickResetButton();  // Сбрасываем прогресс-бар
        progressBarPage.waitForProgressBarToReset();  // Ждём, пока прогресс не сбросится до 0%
    }

    @AfterAll
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
