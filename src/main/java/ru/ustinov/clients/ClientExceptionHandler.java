package ru.ustinov.clients;

import io.swagger.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
        , @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return handleBindingErrors(ex.getBindingResult(), request);
    }

    private ResponseEntity<Object> handleBindingErrors(BindingResult result, WebRequest request) {
        List<String> errorMessages = result.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        String errorMessage = String.join(", ", errorMessages);
        ErrorResponse errorResponse = new ErrorResponse();
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        errorResponse.setPath(httpServletRequest.getMethod() + " " + request.getDescription(false));
        errorResponse.setTitle(errorMessage);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setException(MethodArgumentNotValidException.class.getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NonNull HttpHeaders headers
        , @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        String errorMessage = ex.getMessage();
        // Создаем объект ErrorResponse или другое подходящее представление ошибки
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTitle("Validation Error");
        errorResponse.setTitle(errorMessage);
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setException(HttpMessageNotReadableException.class.getSimpleName()); // Пример типа ошибки
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementException(WebRequest request, NoSuchElementException ex) {
        log.error("NoSuchElementException ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        errorResponse.setPath(httpServletRequest.getMethod() + " " + request.getDescription(false));
        errorResponse.setTitle(ex.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setException(NoSuchElementException.class.getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}
