package demo.springframework.spring6beer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ExceptionController {
    public ResponseEntity handleNotFoundException() {
        System.out.println("In exception handler");

        return  ResponseEntity.notFound().build();
    }
}
