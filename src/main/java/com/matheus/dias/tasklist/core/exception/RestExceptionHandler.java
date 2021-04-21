package com.matheus.dias.tasklist.core.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({ValidationException.class, org.hibernate.exception.ConstraintViolationException.class, ConstraintViolationException.class,
            ValidationException.class})
    public ResponseEntity<Object> handleValidationException(final Exception e) {

        final ApiError error = new ApiError(String.format("Erro no processamento da requisição: %s", e.getMessage()));

        if (LOG.isDebugEnabled()) {
            LOG.debug(error.toString(), e);
        }

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(final EntityNotFoundException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(final HttpClientErrorException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(e.getMessage(), e);
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(e.getResponseBodyAsByteArray());
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.status(e.getRawStatusCode())
                .headers(e.getResponseHeaders())
                .body(resource);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(e.getMessage(), e);
        }

        final ApiError error = new ApiError("Parâmetro obrigatŕio não informado.", Collections.singletonMap(e.getParameterName(), e.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(new HttpHeaders())
                .body(error);
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handle(final ClientAbortException e) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cliente abortou a conexão", e);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUncaughtException(final Exception e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Ocorreu um erro interno."));
    }

    @ExceptionHandler({TransactionSystemException.class})
    protected ResponseEntity<Object> handlePersistenceException(final Exception ex) {
        LOG.info(ex.getClass().getName());

        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {

            ConstraintViolationException consEx = (ConstraintViolationException) cause;
            final List<String> errors = new ArrayList<String>();
            for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
                errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }

            final ApiError apiError = new ApiError(String.format("Erro ao processar a requisição: %s", errors));
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("Ocorreu um erro interno."));
    }
}
