package com.cun.reto1.questions;

import com.cun.reto1.context.ApiResponseContext;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class UsersCount implements Question<Integer> {

    private final ApiResponseContext apiResponseContext;

    public UsersCount(ApiResponseContext apiResponseContext) {
        this.apiResponseContext = apiResponseContext;
    }

    public static UsersCount from(ApiResponseContext apiResponseContext) {
        return new UsersCount(apiResponseContext);
    }

    @Override
    public Integer answeredBy(Actor actor) {
        String responseBody = apiResponseContext.getResponseBody();

        if (responseBody == null || responseBody.isBlank()) {
            return 0;
        }

        JsonPath jsonPath = new JsonPath(responseBody);
        List<?> users = jsonPath.getList("data");

        return users == null ? 0 : users.size();
    }
}