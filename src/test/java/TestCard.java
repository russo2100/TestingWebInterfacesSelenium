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
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алёна Иванова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("++79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
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

    @Test
    void shouldNotValidateNameInLatin() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Latypov Ruslan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateNameInCyrillicAndLatin() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("LATYPOV RUSLAN");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateNumbersInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("1234567");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateSpecCharactersInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("!№ %:,.;`");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateZeroInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("0лег Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateSpacesInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("           ");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateLettersInPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79998883322.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidatePhoneNumberWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79998883322");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79998883322.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateSpecCharactersInPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("!#$ %^ &*");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79998883322.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateLatinLettersInPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Phone number");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79998883322.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateSpacesInPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("           ");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateEmptyForm() {
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79031234567");
        driver.findElement(By.tagName("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

}
