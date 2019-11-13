package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direcao")
public class Controller  {

    @RequestMapping(value = "/cima", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String cima() {
        return "cima";
    }

    @RequestMapping(value = "/baixo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String baixo() {
        return "baixo";
    }

    @RequestMapping(value = "/direita", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String direita() {
        return "direita";
    }

    @RequestMapping(value = "/esquerda", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String esquerda() {
        return "esquerda";
    }
}
