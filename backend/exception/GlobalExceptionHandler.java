package com.expanse.expanse.manager.exception;


import com.expanse.expanse.manager.dto.ResponseDto;
import com.expanse.expanse.manager.exception.notUsing.Abhi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        String firstErrorMessage = errors.values().stream().findFirst().orElse("Validation error");
        ResponseDto response = new ResponseDto();
        response.setMessage(firstErrorMessage);
        response.setTime(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPasswordException(Exception e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setTime(LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(Exception e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setTime(LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(Exception e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setTime(LocalDateTime.now());
        return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<?> handleExpenseNotFoundException(Exception e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(e.getMessage());
        responseDto.setTime(LocalDateTime.now());
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(DBException.class)
    public ResponseEntity<?> handleDBException(Exception e)
    {
        ResponseDto rdto=new ResponseDto();
        rdto.setMessage(e.getMessage());
        rdto.setTime(LocalDateTime.now());
        return ResponseEntity.badRequest().body(rdto);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){

        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
