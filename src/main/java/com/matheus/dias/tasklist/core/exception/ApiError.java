package com.matheus.dias.tasklist.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String message;
    private String classificationCode;
    private Map detail;

    public ApiError(final String message) {
        this(message, null, null);
    }

    public ApiError(final String message, final String classificationCode, final Map detail) {
        this.message = message;
        this.classificationCode = classificationCode;
        this.detail = detail;
    }

    public ApiError(final String message, final String classificationCode) {
        this(message, classificationCode, null);
    }

    public ApiError(final String message, final Map detail) {
        this(message, null, detail);
    }

    private ApiError() {
    }

    public Map getDetail() {
        return detail;
    }

    public String getMessage() {
        return message;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "message='" + message + '\'' +
                ", classificationCode='" + classificationCode + '\'' +
                ", detail=" + detail +
                '}';
    }

    public static class Builder {

        private final ApiError apiError = new ApiError();

        public Builder message(final String message) {
            apiError.message = message;
            return this;
        }

        public Builder classificationCode(final String classificationCode) {
            apiError.classificationCode = classificationCode;
            return this;
        }

        public Builder detail(final Map detail) {
            apiError.detail = detail;
            return this;
        }

        public ApiError build() {
            return apiError;
        }
    }
}
