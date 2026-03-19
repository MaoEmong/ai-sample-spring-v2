package com.example.demo._core.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String ex(RuntimeException e) {
        String body = """
                <script>
                    alert("%s");
                    history.back();
                </script>
                """.formatted(e.getMessage());
        return body;
    }
}
