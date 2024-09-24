package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.LinksPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LinksTest {

    private LinksPage linksPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/links");
        getWebDriver().manage().window().maximize();
        linksPage = new LinksPage();
    }

    @Test
    public void testSimpleLink() {
        linksPage.clickSimpleLink();
    }

    @Test
    public void testDynamicLink() {
        linksPage.clickDynamicLink();
    }

    @Test
    public void testCreatedLink() {
        linksPage.clickCreatedLink();
    }

    @Test
    public void testNoContentLink() {
        linksPage.clickNoContentLink();
    }

    @Test
    public void testMovedLink() {
        linksPage.clickMovedLink();
    }

    @Test
    public void testBadRequestLink() {
        linksPage.clickBadRequestLink();
    }

    @Test
    public void testUnauthorizedLink() {
        linksPage.clickUnauthorizedLink();
    }

    @Test
    public void testForbiddenLink() {
        linksPage.clickForbiddenLink();
    }

    @Test
    public void testNotFoundLink() {
        linksPage.clickNotFoundLink();
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
