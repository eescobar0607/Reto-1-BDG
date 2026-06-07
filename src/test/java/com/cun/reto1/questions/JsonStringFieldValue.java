package com.cun.reto1.questions;

import com.cun.reto1.context.ApiResponseContext;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class JsonStringFieldValue implements Question<String> {

    private final ApiResponseContext apiResponseContext;
    private final String fieldName;

    public JsonStringFieldValue(ApiResponseContext apiResponseContext, String fieldName) {
        this.apiResponseContext = apiResponseContext;
        this.fieldName = fieldName;
    }

    public static JsonStringFieldValue from(ApiResponseContext apiResponseContext, String fieldName) {
        return new JsonStringFieldValue(apiResponseContext, fieldName);
    }

    @Override
    public String answeredBy(Actor actor) {
        String responseBody = apiResponseContext.getResponseBody();

        if (responseBody == null || responseBody.isBlank()) {
            return null;
        }

        JsonPath jsonPath = new JsonPath(responseBody);
        return jsonPath.getString(fieldName);
    }
}