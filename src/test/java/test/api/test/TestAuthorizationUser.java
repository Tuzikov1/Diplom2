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


public class TestAuthorizationUser extends Util {

    @Before
    public void setUp() {
        Response response = step.getCreateUser(email, password, name);
        //Получение токена для дальнейшего удаления пользователя
        token = response.then()
                .extract().jsonPath().getString("accessToken");
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void authorizationUser() {
        step.getAuthorizationUser(email, password, name)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин c некорректным email")
    public void loginWithIncorrectEmail() {
        step.getAuthorizationUser("Тест@mail.ru", password, name)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин c некорректным паролем")
    public void loginWithIncorrectPassword() {
        step.getAuthorizationUser(email, "пароль", name)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    @DisplayName("Удаление пользователя")
    public void deleteUser() {
        step.getDeleteUser(token);
    }
}
