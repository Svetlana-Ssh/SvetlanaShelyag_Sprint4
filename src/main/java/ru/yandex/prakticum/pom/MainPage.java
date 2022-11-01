package ru.yandex.prakticum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;
    private final String url = "https://qa-scooter.praktikum-services.ru/";

    // Локатор кнопки для принятия куки:
    private final By acceptCookiesButton = By.id("rcc-confirm-button");
    // Локатор раздела вопросов и ответов "Вопросы о важном":
    private final By questionsAndAnswersSection = By.xpath(".//div[text()='Вопросы о важном']");
    // Для построение локаторов вопросов и ответов с индексами:
    private final String questionLocatorBase = "accordion__heading-";
    private final String answerLocatorBase = ".//div[@id='accordion__panel-";
    private final String answerLocatorSuffix = "']/p";

    // Локатор кнопки "Заказать" в хедере:
    private final By orderInHeaderButton = By.xpath(".//button[@class='Button_Button__ra12g']");
    // Локатор кнопки "Заказать" в странице:
    private final By orderInContextButton = By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }
    // Метод кликает по кнопке акцепта куки, если эта кнопка отображается на странице:
    public void clickAcceptCookiesButton() {
        if (driver.findElement(acceptCookiesButton).isDisplayed()) {
            driver.findElement(acceptCookiesButton).click();
        }
    }
    // Метод прокрутки страницы до раздела вопросов и ответов:
    public void scrollToQuestionsAndAnswersSection() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(questionsAndAnswersSection));
    }
    // Метод кликает на вопрос с заданным индексом:
    public void clickQuestion(int index) {
        String questionLocator = questionLocatorBase + index;
        driver.findElement(By.id(questionLocator)).click();
    }
    // Метод возвращает текст вопроса с заданаыыми индексом:
    public String getQuestionText(int index) {
        String questionLocator = questionLocatorBase + index;
        return driver.findElement(By.id(questionLocator)).getText();
    }
    // Метод возвращает текст ответа с заданаыыми индексом:
    public String getAnswerText(int index) {
        String answerLocator = answerLocatorBase + index + answerLocatorSuffix;
        return driver.findElement(By.xpath(answerLocator)).getText();
    }
    // Метод кликает на кнопку "Заказать" вверху страницы:
    public void clickOrderInHeaderButton() {
        driver.findElement(orderInHeaderButton).click();
    }
    // Метод кликает на кнопку "Заказать" в теле страницы:
    public void clickOrderInContextButton() {
        driver.findElement(orderInContextButton).click();
    }
    // Метод прокрутки страницы до кнопки "Заказать":
    public void scrollToOrderInContextButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(orderInContextButton));
    }
}
