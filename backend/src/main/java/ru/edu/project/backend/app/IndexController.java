package ru.edu.project.backend.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * Точка входа.
     *
     * @return view
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
