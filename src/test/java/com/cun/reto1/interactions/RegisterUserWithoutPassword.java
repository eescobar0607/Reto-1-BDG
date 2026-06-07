package com.cun.reto1.interactions;

import com.cun.reto1.models.RegisterUserWithoutPasswordRequest;
import com.cun.reto1.utils.ApiConfig;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class RegisterUserWithoutPassword extends RestInteraction {

    private final RegisterUserWithoutPasswordRequest request;

    public RegisterUserWithoutPassword(RegisterUserWithoutPasswordRequest request) {
        this.request = request;
    }

    public static RegisterUserWithoutPassword withMissingPassword() {
        return Tasks.instrumented(
                RegisterUserWithoutPassword.class,
                new RegisterUserWithoutPasswordRequest("sydney@fife")
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