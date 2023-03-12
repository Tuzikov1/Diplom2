package test.api.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Test;
import test.data.Util;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;


public class TestCreateUser extends Util {


    @Test
    @DisplayName("Создание пользователя")
    public void creatingUserPositiveTest() {
        Response response = step.getCreateUser(email, password, name);
        //Получение токена для дальнейшего удаления пользователя
        token = response.then()
                .extract().jsonPath().getString("accessToken");
        response.then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void creatingDoubleUser() {
        //Создаем первого пользователя
        Response response = step.getCreateUser(email, password, name);
        token = response.then()
                .extract().jsonPath().getString("accessToken");
        //Создаем дубль пользователя
        step.getCreateUser(email, password, name)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    public void creatingUserWithoutEmail() {
        step.getCreateUser(null, password, name)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без поля password")
    public void creatingUserWithoutPassword() {
        step.getCreateUser(email, null, name)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    public void creatingUserWithoutName() {
        step.getCreateUser(email, password, null)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        step.getDeleteUser(token);
    }
}
