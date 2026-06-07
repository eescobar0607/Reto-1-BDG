package com.cun.reto1.interactions;

import com.cun.reto1.utils.ApiConfig;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class GetSingleUser extends RestInteraction {

    private final int userId;

    public GetSingleUser(int userId) {
        this.userId = userId;
    }

    public static GetSingleUser withId(int userId) {
        return Tasks.instrumented(GetSingleUser.class, userId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", ApiConfig.API_KEY)
                .get(ApiConfig.BASE_URL + "/users/" + userId);
    }
}