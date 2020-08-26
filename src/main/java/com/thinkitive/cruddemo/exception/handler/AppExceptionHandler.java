package com.thinkitive.cruddemo.exception.handler;

import com.thinkitive.cruddemo.exception.ResourceAlreadyExistsException;
import com.thinkitive.cruddemo.exception.ResourceNotFoundException;
import com.thinkitive.cruddemo.exception.model.ErrorListMessages;
import com.thinkitive.cruddemo.exception.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thinkitive.cruddemo.shared.types.Errors.*;
import static com.thinkitive.cruddemo.shared.utils.MsgSrcUtils.getMessage;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public AppExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("ResourceNotFoundException -- {} -- {}", ex.getMessageCode(), ex.getErrorInfo());

        ErrorMessage errorMessage = new ErrorMessage(
                getMessage(messageSource, ex.getMessageCode()), RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND.value(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        log.error("ResourceAlreadyExistsException -- {} -- {}", ex.getMessageCode(), ex.getErrorInfo());

        ErrorMessage errorMessage = new ErrorMessage(
                getMessage(messageSource, ex.getMessageCode()), RESOURCE_ALREADY_EXISTS,
                HttpStatus.CONFLICT.value(), request.getRequestURI());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messageCodes = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();

        List<String> messages = bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String messageCode = fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : fieldError.getField();
                    messageCodes.add(messageCode);
                    return getMessage(messageSource, messageCode);
                })
                .sorted()
                .collect(Collectors.toList());

        log.error("MethodArgumentNotValidException -- validation.error -- {}", String.join(" ", messageCodes));

        ErrorListMessages errorMessage = new ErrorListMessages(
                messages, VALIDATION_ERROR,
                HttpStatus.BAD_REQUEST.value(), request.getDescription(false).substring(4));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
