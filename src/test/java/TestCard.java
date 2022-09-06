import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestCard {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void shouldSendFormPopularName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Руслан Латыпов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormNameWithDash() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Руслан Дамирович-Латыпов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormDoubleName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Латыпов Руслан Дамирович");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormTripleNameWithDash() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Латыпов Руслан-Дамирович-Латыпов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormNameWithYOLetter() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Семён Сачков");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormCapsName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("ЛАТЫПОВ РУСЛАН");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79031234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

}
