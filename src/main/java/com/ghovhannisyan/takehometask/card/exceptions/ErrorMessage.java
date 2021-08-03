package com.ghovhannisyan.takehometask.card.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {

    NOT_FOUND(HttpStatus.NOT_FOUND, "Object is not found."),
    INVALID_CARD_TYPE(HttpStatus.BAD_REQUEST, "Invalid card type."),
    INVALID_CARD_SUBTYPE(HttpStatus.BAD_REQUEST, "Invalid card subtype.");

    private HttpStatus httpStatus;

    private String errorMessage;

    ErrorMessage(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
