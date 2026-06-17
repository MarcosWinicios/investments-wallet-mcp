package com.demo.investments_wallet.api.exceptionHandler;

import com.demo.investments_wallet.domain.exception.DomainValidationException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


//@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_GENERIC_ERROR_FINAL_USER = "An unexpected error occurred in the system. " +
            "Please try again, and if the problem persists, contact your system administrator.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ex.printStackTrace();

        Problem problem = this.createProblemBuilder(status, problemType,MSG_GENERIC_ERROR_FINAL_USER)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

        String detail =  String.format("The resource '%s' that you tried to access does not exist.", ex.getResourcePath());

        Problem problem = this.createProblemBuilder(this.status(status), problemType, detail)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if(ex instanceof MethodArgumentTypeMismatchException){
            return this.handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String detail = String.format(
                "The URL parameter '%s' received the value '%s', which is an invalid type. Correct this and provide " +
                "a value corresponding to the type '%s'.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()
        );

        Problem problem = this.createProblemBuilder(this.status(status), problemType, detail)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * Handle exceptions related to unreadable HTTP messages, such as JSON parsing errors.<br>
     * Possibles causes include syntax errors in the request body or type mismatches.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return this.handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException) {
            return this.handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
        String detail = "The request body is invalid. Please check for syntax errors.";

        Problem problem = createProblemBuilder(this.status(status), problemType, detail)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * <b>PropertyBindingException</b> is the parent of the UnrecognizedPropertyException and IgnoredPropertyException
     * exceptions.
     * <br>
     * - <b>UnrecognizedPropertyException:</b> When the Jackson attempts to serialize a non-existent field in the target
     * class
     * <br>
     * - <b>IgnoredPropertyException:</b> When Jackson attempts to serialize a field annotated with the @JsonIgnore
     * annotation in the target class.
     */
    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = this.joinPath(ex.getPath());

        ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;

        String detail = String.format(
                "The field '%s' was not recognized. Correct or remove this field and try again.", path
        );

        Problem problem = createProblemBuilder(this.status(status), problemType, detail)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }


    /**
     * InvalidFormatException is a child of MismatchedInputException and is thrown when a JSON property receives a
     * value of an incompatible type.
     * <p>
     * <b>Hierarchy of exceptions:</b> HttpMessageNotReadableException -> MismatchedInputException -> InvalidFormatException
     */
    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = this.joinPath(ex.getPath());

        ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;

        String detail = String.format(
                "The property '%s' received the value '%s', which is an invalid type. Correct this and provide a " +
                "valid value with the type '%s'.",
                path, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        Problem problem = createProblemBuilder(this.status(status), problemType, detail)
                .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
//        HttpStatus status = HttpStatus.NOT_FOUND;
//        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
//        String detail = ex.getMessage();
//        Problem problem = createProblemBuilder(status, problemType, detail)
//                .userMessage(detail)
//                .build();
//
//        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<?> handleBusiness(DomainValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_EXCEPTION;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
//
//    @ExceptionHandler(EntityInUseException.class)
//    public ResponseEntity<?> handleEntityInUse(EntityInUseException ex, WebRequest request) {
//        HttpStatus status = HttpStatus.CONFLICT;
//        ProblemType problemType = ProblemType.ENTITY_IN_USE;
//        String detail = ex.getMessage();
//        Problem problem = createProblemBuilder(status, problemType, detail)
//                .userMessage(detail)
//                .build();
//
//        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {

        if (body == null) {
            HttpStatus httpStatus = HttpStatus.valueOf(statusCode.value());
            body = Problem.builder()
                    .title(httpStatus.getReasonPhrase())
                    .status(statusCode.value())
                    .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title(body.toString())
                    .status(statusCode.value())
                    .timestamp(LocalDateTime.now())
                    .userMessage(MSG_GENERIC_ERROR_FINAL_USER)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, webRequest);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private HttpStatus status(HttpStatusCode  httpStatusCode) {
        return HttpStatus.valueOf(httpStatusCode.value());
    }
}
