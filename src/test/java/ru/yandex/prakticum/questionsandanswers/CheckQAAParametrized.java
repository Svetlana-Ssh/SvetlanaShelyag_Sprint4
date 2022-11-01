package ru.yandex.prakticum.questionsandanswers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.prakticum.pom.MainPage;

import java.time.Duration;

@RunWith(Parameterized.class)
public class CheckQAAParametrized {
    private final int index;
    private final String question;
    private final String answer;

    public CheckQAAParametrized(int index, String question, String answer) {
        this.index = index;
        this.question = question;
        this.answer = answer;
    }

    @Parameterized.Parameters
    //Набор тестовых данных. Проверяется порядковый номер вопроса и соответствие отображаемого ответа.
    public static Object[][] checkQAA() {
        return new Object[][]{
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\WebDriver\\bin\\geckodriver.exe");

        driver = new ChromeDriver();
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void shouldBeQAA() {

        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        //акцептим кукки, иначе это может мешать поиску элемента на странице. Проявлялось в Chrome на последнем вопросе.
        mainPage.clickAcceptCookiesButton();

        //скроллим страницу до секции Вопросов и Ответов, чтобы элементы Вопросов и Ответов отрисовались и начали искаться на странице.
        mainPage.scrollToQuestionsAndAnswersSection();

        //после клика на вопрос должен появиться соответствующий ответ.
        mainPage.clickQuestion(index);
        Assert.assertEquals("Сравнение вопроса", question, mainPage.getQuestionText(index));
        Assert.assertEquals("Cравнение ответа",answer, mainPage.getAnswerText(index));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
