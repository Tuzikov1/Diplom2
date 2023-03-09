package steps;

import io.restassured.response.Response;
import pojo.CreateOrders;
import pojo.CreatingUser;
import pojo.UpdateUser;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiRegister extends BaseApi {
    private final String API_CREATING_USER="/api/auth/register";
    private final String API_AUTHORIZATION="/api/auth/login";
    private final String API_DELETE_UPDATE_USER="/api/auth/user";
    private final String API_CREATE_ORDER="/api/orders";

    public Response getCreateUser(String email, String password, String name){
        return given()
                .spec(specification)
                .body(new CreatingUser(email,password,name))
                .when()
                .post(API_CREATING_USER);
    }

    public Response getAuthorizationUser(String email, String password, String name){
        return given()
                .spec(specification)
                .body(new CreatingUser(email,password,name))
                .when()
                .post(API_AUTHORIZATION);
    }
    public Response getDeleteUser(String token){
        return given()
                .spec(specification)
                .header("Authorization",token)
                .when()
                .delete(API_DELETE_UPDATE_USER);
    }
    public Response getUpdateUser(String email, String password, String name, String token){
        return given()
                .spec(specification)
                .header("Authorization",token)
                .body(new UpdateUser(email,password,name))
                .when()
                .patch(API_DELETE_UPDATE_USER);
    }
    public Response getCreateOrder(ArrayList<String> ingredients,String token){
        return given()
                .spec(specification)
                .header("Authorization",token)
                .body(new CreateOrders(ingredients))
                .when()
                .post(API_CREATE_ORDER);
    }
    public Response getReceivingOrders(String token){
        return given()
                .spec(specification)
                .header("Authorization",token)
                .when()
                .get(API_CREATE_ORDER);
    }

}

