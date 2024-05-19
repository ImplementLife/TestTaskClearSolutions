package il.testtask.clearsolutions.controller.except;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation exception", ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();
        String simpleName = ex.getTarget().getClass().getSimpleName();
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error.getArguments()[0] instanceof DefaultMessageSourceResolvable args) {
                String code = args.getCode();
                builder.append(simpleName).append(".").append(code).append(" ").append(error.getDefaultMessage()).append("\n");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("" + builder);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Bad request exception", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
