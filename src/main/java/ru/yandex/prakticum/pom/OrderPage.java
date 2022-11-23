package ru.yandex.prakticum.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderPage {

    private WebDriver driver;

    // Поле ввода Имени:
    private final By nameInput = By.xpath(".//input[@placeholder = '* Имя']");
    // Поле ввода Фамилии:
    private final By surnameInput = By.xpath(".//input[@placeholder = '* Фамилия']");
    // Поле ввода Адреса:
    private final By addressInput = By.xpath(".//input[@placeholder = '* Адрес: куда привезти заказ']");
    // Поле ввода Станции метро:
    private final By metroInput = By.xpath(".//input[@placeholder = '* Станция метро']");
    // Поле ввода телефона:
    private final By phoneInput = By.xpath(".//input[@placeholder = '* Телефон: на него позвонит курьер']");
    // Поле выбора "Когда привезти самокат", дата начала аренды:
    private final By startDateInput = By.xpath(".//input[@placeholder = '* Когда привезти самокат']");
    // Поле выбора срока аренды
    private final By durationDropDownListInput = By.xpath(".//div[text() = '* Срок аренды']");
    // Выпадающий список - варианты для продолжительности аренды:
    private final By durationOptionsDropDownList = By.xpath(".//div[@class = 'Dropdown-option']");
    // Необязательное. Чекбоксы для выбора цвета самоката:
    private final By colorBlackCheckbox = By.xpath(".//input[@id = 'black']");
    private final By colorGrayCheckbox = By.xpath(".//input[@id = 'gray']");
    // Необязательное. Поле ввода коментария для курьера:
    private final By commentsInput = By.xpath(".//input[@placeholder = 'Комментарий для курьера']");


    // Локатор кнопки "Далее" на шаге "Для кого самокат":
    private final By nextPersonFormButton = By.xpath(".//button[text() = 'Далее']");

    // Лоактор кнопки "Заказать" под данными о заказе на шаге "Про аренду":
    private final By orderFinalButton = By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");

    // Локатор кнопки подтверждения заказа "Да":
    private final By orderConfirmationButton = By.xpath(".//button[text() = 'Да']");

    // Локатор раздела с сообщением подтвержения "Заказ оформлен":
    private final By orderCompletedMsg = By.xpath(".//div[text() = 'Заказ оформлен']");


    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Методы заполняют данные формы заказа:
    public void inputName(String text) {
        driver.findElement(nameInput).sendKeys(text);
    }
    public void inputSurname(String text) {
        driver.findElement(surnameInput).sendKeys(text);
    }
    public void inputAddress(String text) {
        driver.findElement(addressInput).sendKeys(text);
    }
    public void inputMetro(String text) {
        driver.findElement(metroInput).click();
        driver.findElement(metroInput).sendKeys(text, Keys.ARROW_DOWN, Keys.ENTER);
    }
    public void inputPhone(String text) {
        driver.findElement(phoneInput).sendKeys(text);
    }
    public void setStartDateInput(String text) {
        driver.findElement(startDateInput).click();
        driver.findElement(startDateInput).sendKeys(text, Keys.ENTER);
    }
    public void setDurationDropDownListInput(int day) {
        driver.findElement(durationDropDownListInput).click();
        driver.findElements(durationOptionsDropDownList).get(day).click();
    }
    public void setColorCheckbox(String color) {
        if ("black".equals(color)) {
            driver.findElement(colorBlackCheckbox).click();
        } else if ("grey".equals(color))  {
            driver.findElement(colorGrayCheckbox).click();
        }
    }
    public void setCommentsInput(String comments) {
        driver.findElement(commentsInput).sendKeys(comments);
    }

    // Метод кликает на кнопку "Далее" на шаге заполнения "Для кого самокат":
    public void clickNextPersonFormButton() {
        driver.findElement(nextPersonFormButton).click();
    }
    // Метод кликает на кнопку "Заказать" под данными о заказе на шаге "Про аренду":
    public void clickOrderFinalButton() {
        driver.findElement(orderFinalButton).click();
    }
    // Метод кликает на кнопку "Да" подтверждения заказа:
    public void clickOrderConfirmationButton() {
        driver.findElement(orderConfirmationButton).click();
    }
    // Метод проверяет, что отобразилось сообщение "Заказ оформлен":
    public boolean isOrderCompletedMsg() {
        return driver.findElements(orderCompletedMsg).size() > 0;
    }
}
