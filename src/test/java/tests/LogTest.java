package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.LogUtil;

public class LogTest {

    @BeforeEach
    public void setUp() {
        // Дополнительная настройка может быть добавлена здесь, если нужно
    }

    @Test
    public void testLogging() {
        LogUtil.info("This is an info log message");
        LogUtil.error("This is an error log message", new RuntimeException("Test exception"));
        LogUtil.debug("This is a debug log message");
    }
}
