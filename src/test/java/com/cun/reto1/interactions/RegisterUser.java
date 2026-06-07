package com.cun.reto1.interactions;

import com.cun.reto1.models.RegisterUserRequest;
import com.cun.reto1.utils.ApiConfig;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class RegisterUser extends RestInteraction {

    private final RegisterUserRequest request;

    public RegisterUser(RegisterUserRequest request) {
        this.request = request;
    }

    public static RegisterUser withValidCredentials() {
        return Tasks.instrumented(
                RegisterUser.class,
                new RegisterUserRequest("eve.holt@reqres.in", "pistol")
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", ApiConfig.API_KEY)
                .body(request)
                .post(ApiConfig.BASE_URL + "/register");
    }
}