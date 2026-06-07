package com.cun.reto1.questions;

import com.cun.reto1.context.ApiResponseContext;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseStatusCode implements Question<Integer> {

    private final ApiResponseContext apiResponseContext;

    public ResponseStatusCode(ApiResponseContext apiResponseContext) {
        this.apiResponseContext = apiResponseContext;
    }

    public static ResponseStatusCode from(ApiResponseContext apiResponseContext) {
        return new ResponseStatusCode(apiResponseContext);
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return apiResponseContext.getStatusCode();
    }
}