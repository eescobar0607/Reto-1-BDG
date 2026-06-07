package com.cun.reto1.interactions;

import com.cun.reto1.models.UpdateUserRequest;
import com.cun.reto1.utils.ApiConfig;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class UpdateUser extends RestInteraction {

    private final int userId;
    private final UpdateUserRequest request;

    public UpdateUser(int userId, UpdateUserRequest request) {
        this.userId = userId;
        this.request = request;
    }

    public static UpdateUser withIdAndValidData(int userId) {
        return Tasks.instrumented(
                UpdateUser.class,
                userId,
                new UpdateUserRequest("morpheus", "zion resident")
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", ApiConfig.API_KEY)
                .body(request)
                .put(ApiConfig.BASE_URL + "/users/" + userId);
    }
}