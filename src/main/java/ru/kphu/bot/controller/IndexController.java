package ru.kphu.bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("pageId", UUID.randomUUID().toString());
        return "index";
    }
}
