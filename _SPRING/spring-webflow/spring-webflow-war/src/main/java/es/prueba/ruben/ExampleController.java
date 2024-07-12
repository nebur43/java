package es.prueba.ruben;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExampleController {

    @RequestMapping("/start")
    public String start() {
        return "redirect:/ejemplo-flujo";
    }
}