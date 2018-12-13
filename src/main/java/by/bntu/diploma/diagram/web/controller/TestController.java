package by.bntu.diploma.diagram.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {


    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public String findDiagramByUUID() {
        return "Just do it";
    }

}
