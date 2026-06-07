package com.cun.reto1.questions;

import com.cun.reto1.context.ApiResponseContext;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class JsonFieldValue implements Question<Object> {

    private final ApiResponseContext apiResponseContext;
    private final String fieldName;

    public JsonFieldValue(ApiResponseContext apiResponseContext, String fieldName) {
        this.apiResponseContext = apiResponseContext;
        this.fieldName = fieldName;
    }

    public static JsonFieldValue from(ApiResponseContext apiResponseContext, String fieldName) {
        return new JsonFieldValue(apiResponseContext, fieldName);
    }

    @Override
    public Object answeredBy(Actor actor) {
        String responseBody = apiResponseContext.getResponseBody();

        if (responseBody == null || responseBody.isBlank()) {
            return null;
        }

        JsonPath jsonPath = new JsonPath(responseBody);
        return jsonPath.get(fieldName);
    }
}