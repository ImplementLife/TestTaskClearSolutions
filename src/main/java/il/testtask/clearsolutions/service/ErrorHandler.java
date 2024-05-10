package il.testtask.clearsolutions.service;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();
        String simpleName = ex.getTarget().getClass().getSimpleName();
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error.getArguments()[0] instanceof DefaultMessageSourceResolvable args) {
                String code = args.getCode();
                builder.append(simpleName).append(".").append(code).append(" ").append(error.getDefaultMessage()).append("\n");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("" + builder);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
