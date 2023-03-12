package site.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.pojo.CreateOrders;
import site.stellarburgers.pojo.CreatingUser;
import site.stellarburgers.pojo.UpdateUser;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ApiRegister extends BaseApi {
    private final String API_CREATING_USER = "/api/auth/register";
    private final String API_AUTHORIZATION = "/api/auth/login";
    private final String API_DELETE_UPDATE_USER = "/api/auth/user";
    private final String API_CREATE_ORDER = "/api/orders";

    @Step("Создание пользователя")
    public Response getCreateUser(String email, String password, String name) {
        return given()
                .spec(specification)
                .body(new CreatingUser(email, password, name))
                .when()
                .post(API_CREATING_USER);
    }

    @Step("Авторизация пользователя")
    public Response getAuthorizationUser(String email, String password, String name) {
        return given()
                .spec(specification)
                .body(new CreatingUser(email, password, name))
                .when()
                .post(API_AUTHORIZATION);
    }

    @Step("Удаление пользователя")
    public Response getDeleteUser(String token) {
        return given()
                .spec(specification)
                .header("Authorization", token)
                .when()
                .delete(API_DELETE_UPDATE_USER);
    }

    @Step("Изменение данных пользователя")
    public Response getUpdateUser(String email, String password, String name, String token) {
        return given()
                .spec(specification)
                .header("Authorization", token)
                .body(new UpdateUser(email, password, name))
                .when()
                .patch(API_DELETE_UPDATE_USER);
    }

    @Step("Создание заказа")
    public Response getCreateOrder(ArrayList<String> ingredients, String token) {
        return given()
                .spec(specification)
                .header("Authorization", token)
                .body(new CreateOrders(ingredients))
                .when()
                .post(API_CREATE_ORDER);
    }

    @Step("Получение заказа")
    public Response getReceivingOrders(String token) {
        return given()
                .spec(specification)
                .header("Authorization", token)
                .when()
                .get(API_CREATE_ORDER);
    }

}

