package ua.edu.ifntuog.studentportal.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(
            EntityNotFoundException ex) {
        return buildProblemDetail(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));

        if (detail.isBlank()) {
            detail = "Request validation failed";
        }

        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation Failed", detail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex) {
        String detail = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        if (detail.isBlank()) {
            detail = "Request validation failed";
        }

        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Validation Failed", detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
        String detail = ex.getMessage();
        if (detail == null || detail.isBlank()) {
            detail = "An unexpected error occurred";
        }

        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", detail);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(HttpStatus status, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail == null || detail.isBlank() ? status.getReasonPhrase() : detail);
        problemDetail.setTitle(title);
        return ResponseEntity.status(status).body(problemDetail);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
