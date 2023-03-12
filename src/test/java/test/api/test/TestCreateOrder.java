package test.api.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.data.Util;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestCreateOrder extends Util {

    @Before
    public void setUp() {
        Response response = step.getCreateUser(email, password, name);
        //Получение токена для дальнейшего удаления пользователя
        token = response.then()
                .extract().jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutLogin() {
        ingredients.add(ingredient);
        step.getCreateOrder(ingredients, "0")
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithLogin() {
        ingredients.add(ingredient);
        step.getCreateOrder(ingredients, token)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredients() {
        step.getCreateOrder(ingredients, token)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание заказа c некорректным хешем ингридиента")
    public void createOrderWithIncorrectIngredients() {
        ingredients.add("огурец");
        step.getCreateOrder(ingredients, token)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        step.getDeleteUser(token);
    }
}

