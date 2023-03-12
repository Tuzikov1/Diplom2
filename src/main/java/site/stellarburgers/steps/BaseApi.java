package site.stellarburgers.steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseApi {
    RequestSpecification specification = new RequestSpecBuilder()
            .setContentType("application/json")
            .setBaseUri("https://stellarburgers.nomoreparties.site/")
            .build();
}
