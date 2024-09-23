package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.PracticeFormPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class PracticeFormTest {

    private final PracticeFormPage practiceFormPage = new PracticeFormPage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.pageLoadTimeout = 30000;
        Configuration.reopenBrowserOnFail = true;
        open("https://demoqa.com/automation-practice-form");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testFillFirstName() {
        practiceFormPage.fillFirstName("John");
    }

    @Test
    public void testFillLastName() {
        practiceFormPage.fillLastName("Doe");
    }

    @Test
    public void testFillEmail() {
        practiceFormPage.fillEmail("john.doe@example.com");
    }

    @Test
    public void testSelectGender() {
        practiceFormPage.selectGender();
    }

    @Test
    public void testFillMobile() {
        practiceFormPage.fillMobile("1234567890");
    }

    @Test
    public void testFillSubject() {
        practiceFormPage.fillSubject("Math");
    }

    @Test
    public void testSelectDateOfBirth() {
        practiceFormPage.selectDateOfBirth("15", "September", "1990");
    }

    @Test
    public void testSelectHobbies() {
        practiceFormPage.selectHobbies("Sports", "Music", "Reading");
    }

    @Test
    public void testUploadFile() {
        practiceFormPage.uploadFile("C:/Users/Username/IdeaProjects/demoQAmaster/src/main/resources/sampleFile.jpeg");
    }

    @Test
    public void testFillCurrentAddress() {
        practiceFormPage.fillCurrentAddress("123 Main St, Springfield");
    }

    @Test
    public void testSelectStateAndCity() {
        practiceFormPage.selectStateAndCity("NCR", "Delhi");
    }

    @Test
    public void testSubmitForm() {
        practiceFormPage.submitForm();
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
