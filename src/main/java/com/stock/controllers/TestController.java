package com.stock.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class TestController {

    @GetMapping("/")
    public String main(Model model) {
        return "test";
    }
}
