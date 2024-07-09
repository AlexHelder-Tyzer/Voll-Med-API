package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello") // ruta http que sigue el metodo
public class HelloController {

    @GetMapping // mapeo con metodo GET
    public String helloWorld(){
        return "Hello World! from Netherlands";
    }
}
