package jwt_auth.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import jwt_auth.exceptions.LoginException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    String handleHttpMessageNotReadableException(
        HttpMessageNotReadableException ex) {
        return "JSON parse error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    String handleMethodArgumentTypeMismatchException() {
        return "BAD REQUEST";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    String handleDataAccessException() {
        return "INTERNAL SERVER ERROR";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<String>();
        ex.getFieldErrors().forEach((e) -> {
            errors.add(e.getField() + " " + e.getDefaultMessage());
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginException.class)
    String handleLoginException(LoginException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    String handleGenericException(Exception ex) {
        System.err.printf("\n %s \n\n", ex);
        return "INTERNAL SERVER ERROR";
    }

}
