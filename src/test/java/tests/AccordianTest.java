package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import pages.AccordianPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccordianTest {

    private final AccordianPage accordianPage = new AccordianPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/accordian");
        getWebDriver().manage().window().maximize();
    }

    @Test
    @DisplayName("Тестирование открытия и закрытия раздела 1")
    public void testSection1() {
        accordianPage.verifySection1ContentVisible();
        accordianPage.clickSection1Heading();
        accordianPage.verifySection1ContentHidden();
        accordianPage.clickSection1Heading();
        accordianPage.verifySection1ContentVisible();
    }

    @Test
    @DisplayName("Тестирование открытия и закрытия раздела 2")
    public void testSection2() {
        accordianPage.verifySection2ContentHidden();
        accordianPage.clickSection2Heading();
        accordianPage.verifySection2ContentVisible();
        accordianPage.clickSection2Heading();
        accordianPage.verifySection2ContentHidden();
    }

    @Test
    @DisplayName("Тестирование открытия и закрытия раздела 3")
    public void testSection3() {
        accordianPage.verifySection3ContentHidden();
        accordianPage.clickSection3Heading();
        accordianPage.verifySection3ContentVisible();
        accordianPage.clickSection3Heading();
        accordianPage.verifySection3ContentHidden();
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
