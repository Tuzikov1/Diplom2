import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.Util;

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
    @Description("Создание заказа без авторизации")
    public void createOrderWithoutLogin(){
        ingredients.add(ingredient);
        step.getCreateOrder(ingredients,"0")
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("order",notNullValue());
}

     @Test
    @Description("Создание заказа с авторизацией")
    public void createOrderWithLogin(){
        ingredients.add(ingredient);
        step.getCreateOrder(ingredients,token)
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("order",notNullValue());
    }

    @Test
    @Description("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredients(){
        step.getCreateOrder(ingredients,token)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @Description("Создание заказа c некорректным хешем ингридиента")
    public void createOrderWithIncorrectIngredients(){
        ingredients.add("огурец");
        step.getCreateOrder(ingredients,token)
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void deleteUser() {
        step.getDeleteUser(token);
    }
}

