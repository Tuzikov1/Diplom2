import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testData.Util;


import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
public class TestUpdateUser extends Util {

    @Before
    public void setUp() {
        Response response = step.getCreateUser(email, password, name);
        //Получение токена для дальнейшего использования
        token = response.then()
                .extract().jsonPath().getString("accessToken");
    }

    @Test
    @Description("Изменение данных при авторизации")
    public void changeEmailWithToken() {
        step.getUpdateUser("tesron3616@gmail.com", "тестовыйПароль12", "КакоеТоИмя", token)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("success", equalTo(true));
    }
    @Test
    @Description("Изменение данных без")
    public void changeEmailWithoutToken() {
        step.getUpdateUser("comanda369@gmail.com", "ПарольТестовый62", "ТестовоеИмя", "токен")
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void deleteUser(){step.getDeleteUser(token);}
}
