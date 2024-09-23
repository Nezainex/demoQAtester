package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ModalDialogPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ModalDialogTest {

    private final ModalDialogPage modalDialogPage = new ModalDialogPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/modal-dialogs");
        getWebDriver().manage().window().maximize();
    }

    // Тест на открытие и закрытие Small Modal
    @Test
    public void testOpenAndCloseSmallModal() {
        modalDialogPage.openSmallModal();
        modalDialogPage.closeSmallModalWithButton();
    }

    // Тест на проверку текста в Small Modal
    @Test
    public void testVerifySmallModalText() {
        modalDialogPage.openSmallModal();
        modalDialogPage.verifySmallModalText();
        modalDialogPage.closeSmallModalWithButton();
    }

    // Тест на открытие и закрытие Large Modal
    @Test
    public void testOpenAndCloseLargeModal() {
        modalDialogPage.openLargeModal();
        modalDialogPage.closeLargeModalWithButton();
    }

    // Тест на проверку текста в Large Modal
    @Test
    public void testVerifyLargeModalText() {
        modalDialogPage.openLargeModal();
        modalDialogPage.verifyLargeModalText();
        modalDialogPage.closeLargeModalWithButton();
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
