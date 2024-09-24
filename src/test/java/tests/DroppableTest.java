package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.DroppablePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DroppableTest {

    private DroppablePage droppablePage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/droppable");
        getWebDriver().manage().window().maximize();
        droppablePage = new DroppablePage();
    }

    @Test
    public void testSimpleDroppable() {
        open("https://demoqa.com/droppable");  // Перезагрузка страницы
        droppablePage.switchToSimpleTab();  // Переключение на нужный таб
        droppablePage.dragAndDrop(droppablePage.getDragMeSimple(), droppablePage.getDropHereSimple());
        assertTrue(droppablePage.getDropHereSimple().getText().contains("Dropped"), "Элемент должен быть успешно сброшен.");
    }

    @Test
    public void testAcceptDroppable() {
        open("https://demoqa.com/droppable");  // Перезагрузка страницы
        droppablePage.switchToAcceptTab();  // Переключение на нужный таб
        droppablePage.dragAndDrop(droppablePage.getNotAcceptable(), droppablePage.getDropHereAccept());
        assertFalse(droppablePage.getDropHereAccept().getText().contains("Dropped"), "Элемент не должен быть принят.");

        droppablePage.dragAndDrop(droppablePage.getAcceptable(), droppablePage.getDropHereAccept());
        assertTrue(droppablePage.getDropHereAccept().getText().contains("Dropped"), "Элемент должен быть принят.");
    }

    @Test
    public void testPreventPropagationDroppable() {
        open("https://demoqa.com/droppable");  // Перезагрузка страницы
        droppablePage.switchToPreventPropagationTab();  // Переключение на нужный таб

        // Перетаскиваем элемент в не жадный внутренний контейнер
        droppablePage.dragAndDrop(droppablePage.getDragBox(), droppablePage.getInnerNotGreedyDropBox());
        assertTrue(droppablePage.getInnerNotGreedyDropBox().getText().contains("Dropped"), "Элемент должен быть сброшен в не жадный внутренний контейнер.");
        assertTrue(droppablePage.getOuterNotGreedyDropBox().getText().contains("Dropped"), "Элемент должен быть сброшен в не жадный внешний контейнер.");

        // Перетаскиваем элемент в жадный внутренний контейнер
        droppablePage.dragAndDrop(droppablePage.getDragBox(), droppablePage.getInnerGreedyDropBox());
        assertTrue(droppablePage.getInnerGreedyDropBox().getText().contains("Dropped"), "Элемент должен быть сброшен в жадный внутренний контейнер.");
        assertTrue(droppablePage.getOuterGreedyDropBox().getText().contains("Dropped"), "Элемент должен быть сброшен в жадный внешний контейнер.");
    }

    @Test
    public void testRevertDraggable() {
        open("https://demoqa.com/droppable");  // Перезагрузка страницы
        // Переключаемся на вкладку "Revert Draggable"
        droppablePage.switchToRevertDraggableTab();

        // Ожидание видимости элемента "Will Revert"
        droppablePage.getWillRevert().shouldBe(visible);

        // Перетаскивание элемента "Will Revert" и проверка, что он возвращается
        droppablePage.dragAndDrop(droppablePage.getWillRevert(), droppablePage.getDropHereRevert());
        assertTrue(droppablePage.getDropHereRevert().getText().contains("Dropped"), "Элемент должен быть сброшен.");
        // Проверяем, что элемент "Will Revert" вернулся на исходную позицию
        assertEquals(droppablePage.getWillRevert().getCssValue("position"), "relative", "Элемент должен вернуться на исходную позицию.");

        // Ожидание видимости элемента "Not Revert"
        droppablePage.getNotRevert().shouldBe(visible);

        // Сохраняем начальные координаты элемента "Not Revert"
        int notRevertInitialX = droppablePage.getNotRevert().getLocation().getX();
        int notRevertInitialY = droppablePage.getNotRevert().getLocation().getY();

        // Перетаскивание элемента "Not Revert" и проверка, что он НЕ возвращается
        droppablePage.dragAndDrop(droppablePage.getNotRevert(), droppablePage.getDropHereRevert());
        assertTrue(droppablePage.getDropHereRevert().getText().contains("Dropped"), "Элемент должен быть сброшен.");

        // Проверяем, что элемент "Not Revert" НЕ вернулся на исходную позицию
        int notRevertAfterDropX = droppablePage.getNotRevert().getLocation().getX();
        int notRevertAfterDropY = droppablePage.getNotRevert().getLocation().getY();
        assertNotEquals(notRevertAfterDropX, notRevertInitialX, "Элемент не должен вернуться на исходную X координату.");
        assertNotEquals(notRevertAfterDropY, notRevertInitialY, "Элемент не должен вернуться на исходную Y координату.");
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
