package com.example.EverExpanding.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(
            @ModelAttribute("email")
                    String email,
            RedirectAttributes attributes
    ) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("email", email);

        return "redirect:/users/login";
    }
}
