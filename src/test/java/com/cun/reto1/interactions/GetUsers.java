package com.cun.reto1.interactions;

import com.cun.reto1.utils.ApiConfig;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

public class GetUsers extends RestInteraction {

    private final int page;

    public GetUsers(int page) {
        this.page = page;
    }

    public static GetUsers fromPage(int page) {
        return Tasks.instrumented(GetUsers.class, page);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", ApiConfig.API_KEY)
                .get(ApiConfig.BASE_URL + "/users?page=" + page);
    }
}