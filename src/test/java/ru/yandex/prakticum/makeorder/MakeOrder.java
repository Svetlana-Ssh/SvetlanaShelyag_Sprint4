package ru.yandex.prakticum.makeorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.prakticum.pom.MainPage;
import ru.yandex.prakticum.pom.OrderPage;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
// Через параметры в тест будем передавать одну из кнопок "Заказать" с главной страницы и значения параметров для всех полей формы заказа
public class MakeOrder {
    private final String orderButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String startDate;
    private final int orderDays;
    private final String color;
    private final String comments;

    public MakeOrder(String orderButton, String name, String surname, String address, String metro, String phone, String startDate, int orderDays, String color, String comments) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.startDate = startDate;
        this.orderDays = orderDays;
        this.color = color;
        this.comments = comments;
    }

    @Parameterized.Parameters
    //Набор тестовых данных.
    public static Object[][] checkMakeOrder() {
        return new Object[][]{
                // Заказ через кнопку в header. Все поля получают допустимые значения.
                {"orderInHeaderButton", "Иван", "Иванов","ул.Длинная, д.6", "Парк", "89211231212", "10.11.2022", 1, "black", "My comments for order"},
                // Заказ через кнопку в тексте страницы. Значения заданы только для обязательных полей.
                // ***ВОПРОС*** если хочу не передавать необязательные данные - нормально ли делать sendKeys с пустой строкой? Или лучше сделать if, и вообще не вызывать sendKeys?
                {"orderInContextButton", "Петр", "Петров", "Улица", "Чеховская", "89212222222", "23.01.2023", 2, "", ""},
        };
    }
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\WebDriver\\bin\\geckodriver.exe");

        // выбор браузера для прогона тестов:
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void shouldMakeOrder() {

        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        //акцептим кукки, иначе это может мешать поиску элемента на странице. Проявлялось в Chrome на последнем вопросе:
        mainPage.clickAcceptCookiesButton();

        // Клик по указанной в параметре кнопке "Заказать":
        if ("orderInHeaderButton".equals(orderButton)) {
            mainPage.clickOrderInHeaderButton();
        } else if ("orderInContextButton".equals(orderButton)) {
            //скроллим страницу до второй кнопки "Заказать" и кликаем ее:
            //mainPage.scrollToOrderInContextButton();
            mainPage.clickOrderInContextButton();
        }

        OrderPage orderPage = new OrderPage(driver);

        //заполняем поля заказа на шаге "Для кого самокат":
        orderPage.inputName(name);
        orderPage.inputSurname(surname);
        orderPage.inputAddress(address);
        orderPage.inputMetro(metro);
        orderPage.inputPhone(phone);

        //клик кнопки "Далее"
        orderPage.clickNextPersonFormButton();

        //заполняем поля заказа на шаге "Про аренду":
        orderPage.setStartDateInput(startDate);
        orderPage.setDurationDropDownListInput(orderDays);
        orderPage.setColorCheckbox(color);
        orderPage.setCommentsInput(comments);

        //клик по кнопкам "Заказать" и подтверждения заказа "Да":
        orderPage.clickOrderFinalButton();
        orderPage.clickOrderConfirmationButton();
        //Проверка, что появилось окно подтверждения создания заказа "Заказ оформлен":
        assertTrue("Ожидаем появления подтверждения \"Заказ оформлен\".",orderPage.isOrderCompletedMsg());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

