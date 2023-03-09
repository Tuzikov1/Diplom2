import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Test;

import testData.Util;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class TestCreateUser extends Util {


    @Test
    @Description("Создание пользователя")
    public void creatingUserPositiveTest(){
      Response response = step.getCreateUser(email,password,name);
                response.then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("success", equalTo(true));
          //Получение токена для дальнейшего удаления пользователя
       token=response.then()
                .extract().jsonPath().getString("accessToken");
    }

    @Test
    @Description("Создание пользователя, который уже зарегистрирован")
    public void creatingDoubleUser(){
        //Создаем первого пользователя
        Response response = step.getCreateUser(email,password,name);
        token=response.then()
                .extract().jsonPath().getString("accessToken");
        //Создаем дубль пользователя
        step.getCreateUser(email,password,name)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("success", equalTo(false));
}
   @Test
   @Description("Создание пользователя без поля email")
    public void creatingUserWithoutEmail(){
    step.getCreateUser(null,password,name)
            .then()
            .statusCode(SC_FORBIDDEN)
            .and()
            .assertThat()
            .body("message", equalTo("Email, password and name are required fields"));
}
    @Test
    @Description("Создание пользователя без поля password")
    public void creatingUserWithoutPassword(){
        step.getCreateUser(email,null,name)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @Description("Создание пользователя без поля name")
    public void creatingUserWithoutName(){
        step.getCreateUser(email,password,null)
                .then()
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @After
    public void deleteUser(){step.getDeleteUser(token);}
}
