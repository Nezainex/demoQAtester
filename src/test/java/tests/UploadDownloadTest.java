package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import pages.UploadDownloadPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadDownloadTest {

    private UploadDownloadPage uploadDownloadPage;
    private String downloadsFolder;

    @BeforeAll
    public void setUpClass() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";

        // Use system temp directory for downloads
        downloadsFolder = Files.createTempDirectory("downloads").toAbsolutePath().toString();
        Configuration.downloadsFolder = downloadsFolder;

        Configuration.fileDownload = FileDownloadMode.FOLDER;  // Set file download method
        Configuration.timeout = 5000;
        Configuration.reopenBrowserOnFail = true;
        Configuration.pageLoadStrategy = "eager";  // Tests start immediately after DOM loads
        Configuration.pageLoadTimeout = 30000;  // Max 30 seconds for page load
    }

    @BeforeEach
    public void setUp() {
        open("https://demoqa.com/upload-download");
        getWebDriver().manage().window().maximize();
        uploadDownloadPage = new UploadDownloadPage();
    }

    @Test
    @Order(1)
    public void testDownloadFile() throws Exception {
        // Download the file
        File downloadedFile = uploadDownloadPage.downloadFile();

        // Verify the file exists in the downloads folder
        assertTrue(downloadedFile.exists(), "Файл не был скачан.");
        assertEquals("sampleFile.jpeg", downloadedFile.getName(), "Скачан неправильный файл.");
    }

    @Test
    @Order(2)
    public void testUploadFile() {
        // Ensure the downloads folder exists
        Path downloadDir = Paths.get(downloadsFolder);
        if (!Files.exists(downloadDir)) {
            System.out.println("Download directory does not exist: " + downloadDir);
            fail("Download directory does not exist.");
        }

        // Search for the file in the configured downloads folder
        Path downloadPath = findDownloadedFile(downloadDir);

        // Verify that the file was found and exists
        assertNotNull(downloadPath, "Файл для загрузки не найден!");
        assertTrue(Files.exists(downloadPath), "Файл для загрузки не найден!");

        File fileToUpload = downloadPath.toFile();

        // Upload the file through the form
        uploadDownloadPage.uploadFile(fileToUpload);

        // Check that the uploaded file has the correct name
        assertEquals("sampleFile.jpeg", fileToUpload.getName(), "Загружен неправильный файл.");
    }

    /**
     * Method to find the downloaded file by name in all subdirectories of the specified directory.
     */
    private Path findDownloadedFile(Path downloadDir) {
        if (!Files.exists(downloadDir) || !Files.isDirectory(downloadDir)) {
            System.out.println("Download directory does not exist: " + downloadDir);
            return null;
        }

        for (int i = 0; i < 5; i++) {  // Try up to 5 times to find the file
            try (Stream<Path> paths = Files.walk(downloadDir)) {
                Path path = paths
                        .filter(Files::isRegularFile)  // Only files
                        .filter(p -> p.getFileName().toString().equals("sampleFile.jpeg"))  // Check file name
                        .findFirst()
                        .orElse(null);  // Return null if not found

                if (path != null) {
                    return path;  // File found
                }

                Thread.sleep(1000);  // Delay before retry
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;  // If file not found after 5 attempts
    }

    @AfterAll
    public void cleanUp() throws IOException {
        // Close the browser windows
        try {
            closeWebDriver();
        } catch (Exception e) {
            System.out.println("Error while closing the browser: " + e.getMessage());
        }

        // Delete the downloads directory
        File downloadsDir = new File(downloadsFolder);
        if (downloadsDir.exists()) {
            FileUtils.deleteDirectory(downloadsDir);  // Delete the directory and its contents
        } else {
            System.out.println("Директория для загрузок не существует: " + downloadsDir.getAbsolutePath());
        }
    }
}
