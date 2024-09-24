package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pages.SortablePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SortableTest {

    SortablePage sortablePage = new SortablePage();

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Тесты начнутся сразу после загрузки DOM
        Configuration.pageLoadTimeout = 30000;  // Максимум 30 секунд для полной загрузки страницы
        open("https://demoqa.com/sortable");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void testDragAndDropListItems() {
        sortablePage.getListTab().click();

        // Получение элементов списка
        ElementsCollection listItems = sortablePage.getListItems();

        // Перемещение первого элемента на третью позицию
        sortablePage.dragAndDropUsingActions(listItems, 0, 2);

        // Проверка, что элемент успешно перемещен
        listItems = sortablePage.getListItems(); // Обновляем список после перемещения
        assertEquals("One", listItems.get(2).getText());

        // Перемещение третьего элемента на первую позицию
        sortablePage.dragAndDropUsingActions(listItems, 2, 0);

        // Проверка, что элемент успешно перемещен
        listItems = sortablePage.getListItems(); // Обновляем список после перемещения
        assertEquals("One", listItems.get(0).getText());
    }

    @Test
    public void testDragAndDropGridItems() {
        sortablePage.getGridTab().click();

        // Получение элементов сетки
        ElementsCollection gridItems = sortablePage.getGridItems();

        // Перемещение первого элемента на шестую позицию
        sortablePage.dragAndDropUsingActions(gridItems, 0, 5);

        // Проверка, что элемент успешно перемещен
        gridItems = sortablePage.getGridItems(); // Обновляем список после перемещения
        assertEquals("One", gridItems.get(5).getText());

        // Перемещение шестого элемента обратно на первую позицию
        sortablePage.dragAndDropUsingActions(gridItems, 5, 0);

        // Проверка, что элемент успешно перемещен
        gridItems = sortablePage.getGridItems(); // Обновляем список после перемещения
        assertEquals("One", gridItems.first().getText());

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
