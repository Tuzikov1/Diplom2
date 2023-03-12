package test.api.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.data.Util;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class TestReceivingOrders extends Util {

    @Before
    public void setUp() {
        //Создаем пользователя и получаем токен
        Response response = step.getCreateUser(email, password, name);
        token = response.then()
                .extract().jsonPath().getString("accessToken");
        //Создаем заказ авторизованным пользователем
        ingredients.add(ingredient);
        step.getCreateOrder(ingredients, token);
    }

    @Test
    @DisplayName("Просмотр заказа авторизованным пользователем")
    public void receivingOrdersWithLogin() {
        step.getReceivingOrders(token)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Просмотр заказа неавторизованным пользователем")
    public void receivingOrdersWithoutLogin() {
        step.getReceivingOrders("0")
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        step.getDeleteUser(token);
    }
}

