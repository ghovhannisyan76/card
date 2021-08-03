package com.ghovhannisyan.takehometask.card.exceptions;

public class CardApiException extends RuntimeException {

    private ErrorMessage errorMessage;

    private String source;

    public CardApiException(ErrorMessage errorMessage, String detailedMessage) {
        this(errorMessage, detailedMessage, null);
    }

    public CardApiException(ErrorMessage errorMessage, String detailedMessage, String source) {
        super(detailedMessage);
        this.errorMessage = errorMessage;
        this.source = source;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public String getSource() {
        return source;
    }
}
