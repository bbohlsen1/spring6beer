package demo.springframework.spring6beer.controllers;

import org.springframework.http.ResponseEntity;


public class ExceptionController {
    public ResponseEntity handleNotFoundException() {
        System.out.println("In exception handler");

        return  ResponseEntity.notFound().build();
    }
}
