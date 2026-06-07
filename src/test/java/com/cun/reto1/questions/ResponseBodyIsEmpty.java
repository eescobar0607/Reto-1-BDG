package com.cun.reto1.questions;

import com.cun.reto1.context.ApiResponseContext;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseBodyIsEmpty implements Question<Boolean> {

    private final ApiResponseContext apiResponseContext;

    public ResponseBodyIsEmpty(ApiResponseContext apiResponseContext) {
        this.apiResponseContext = apiResponseContext;
    }

    public static ResponseBodyIsEmpty from(ApiResponseContext apiResponseContext) {
        return new ResponseBodyIsEmpty(apiResponseContext);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String responseBody = apiResponseContext.getResponseBody();
        return responseBody == null || responseBody.isBlank();
    }
}