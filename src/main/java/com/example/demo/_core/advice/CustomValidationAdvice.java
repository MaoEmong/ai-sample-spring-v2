package com.example.demo._core.advice;

import com.example.demo._core.utils.Resp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Aspect
@Component
public class CustomValidationAdvice {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    FieldError fieldError = bindingResult.getFieldErrors().get(0);
                    String message = fieldError.getDefaultMessage();
                    throw new RuntimeException(message); // 간단한 구현을 위해 RuntimeException 사용 (GlobalExceptionHandler에서 처리 가능)
                }
            }
        }
    }
}
