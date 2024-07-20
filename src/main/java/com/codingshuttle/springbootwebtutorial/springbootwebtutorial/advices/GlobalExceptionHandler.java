package com.codingshuttle.springbootwebtutorial.springbootwebtutorial.advices;

import com.codingshuttle.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiError> handleNoSuchElementException(ResourceNotFoundException e) {

        ApiError apiError= ApiError.builder().message(e.getMessage()).status(HttpStatus.NOT_FOUND).build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalServerErorr(Exception e) {

        ApiError apiError= ApiError.builder().message(e.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidErorr(MethodArgumentNotValidException e) {
        List<String> erorrs=e.getBindingResult().
                getAllErrors().stream().map(
                error -> error.getDefaultMessage()
        ).collect(Collectors.toList());



        ApiError apiError= ApiError.builder().message("input validation error").subErorr(erorrs).status(HttpStatus.BAD_REQUEST).build();
        return buildErorrResponseEntity(apiError);
    }

    private ResponseEntity<APIResponse<?>> buildErorrResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new APIResponse<>(apiError),apiError.getStatus());
    }


}
