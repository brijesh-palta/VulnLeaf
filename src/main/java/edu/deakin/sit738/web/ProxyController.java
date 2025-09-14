package edu.deakin.sit738.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class ProxyController {

    @GetMapping("/proxy")
    public String proxyForm() {
        return "proxy";
    }

    @PostMapping("/proxy/fetch")
    public String fetchUrl(@RequestParam String target, Model model) {
        RestTemplate rest = new RestTemplate();
        try {
            String body = rest.getForObject(target, String.class);
            model.addAttribute("target", target);
            model.addAttribute("body", body);
        } catch (Exception e) {
            model.addAttribute("target", target);
            model.addAttribute("body", "Error: " + e.getMessage());
        }
        return "proxy-result";
    }
}
