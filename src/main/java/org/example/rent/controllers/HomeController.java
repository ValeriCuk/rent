package org.example.rent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
//        model.addAttribute("contentTemplate", "layouts/index");
//        model.addAttribute("contentFragment", "content");
        return "layouts/base";
    }

}
