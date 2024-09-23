package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DraggablePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class DraggableTest {

    private DraggablePage draggablePage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/dragabble");
        getWebDriver().manage().window().maximize();
        draggablePage = new DraggablePage();
    }

    @Test
    public void testSimpleDraggable() {
        draggablePage.getSimpleTab().click();
        draggablePage.dragAndDrop(draggablePage.getDragBoxSimple(), 100, 100);
        // Здесь можно добавить проверки по координатам после перемещения
    }

    @Test
    public void testAxisRestrictedDraggable() {
        draggablePage.getAxisRestrictedTab().click();

        // Перетаскивание только по оси X
        draggablePage.dragAndDrop(draggablePage.getDragBoxX(), 100, 0);

        // Перетаскивание только по оси Y
        draggablePage.dragAndDrop(draggablePage.getDragBoxY(), 0, 100);
    }

    @Test
    public void testContainerRestrictedDraggable() {
        draggablePage.getContainerRestrictedTab().click();

        // Перетаскивание внутри контейнера
        draggablePage.dragAndDrop(draggablePage.getDragBoxContainer(), 50, 50);

        // Перетаскивание внутри родителя
        draggablePage.dragAndDrop(draggablePage.getDragBoxParent(), 30, 30);
    }

    @Test
    public void testCursorStyleDraggable() {
        draggablePage.getCursorStyleTab().click();

        // Перетаскивание с фиксированным курсором в центре
        draggablePage.dragAndDrop(draggablePage.getDragBoxCursorCenter(), 100, 100);

        // Перетаскивание с курсором в верхнем левом углу
        draggablePage.dragAndDrop(draggablePage.getDragBoxCursorTopLeft(), 100, 100);

        // Перетаскивание с курсором внизу
        draggablePage.dragAndDrop(draggablePage.getDragBoxCursorBottom(), 100, 100);
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
