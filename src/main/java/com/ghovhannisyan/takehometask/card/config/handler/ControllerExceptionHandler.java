package com.ghovhannisyan.takehometask.card.config.handler;

import com.ghovhannisyan.takehometask.card.exceptions.CardApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CardApiException.class)
    protected ResponseEntity<Object> handleCardApiException(CardApiException cardApiException, WebRequest webRequest) {
        com.ghovhannisyan.takehometask.card.model.Error err = new com.ghovhannisyan.takehometask.card.model.Error();
        err.setSource(cardApiException.getSource());
        err.setReasonCode(cardApiException.getErrorMessage().getHttpStatus().name());
        err.setDescription(String.join(" ", cardApiException.getErrorMessage().getErrorMessage(), cardApiException.getMessage()));
        return handleExceptionInternal(cardApiException, err, new HttpHeaders(), cardApiException.getErrorMessage().getHttpStatus(), webRequest);
    }

}
