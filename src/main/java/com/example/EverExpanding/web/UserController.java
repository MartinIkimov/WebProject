package com.example.EverExpanding.web;

import com.example.EverExpanding.model.binding.UserRegisterBindingModel;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.model.view.UserViewModel;
import com.example.EverExpanding.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

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

    @GetMapping("/register")
    public String register(Model model) {
        if (model.containsAttribute("isEmailUnique")) {
            model.addAttribute("isEmailUnique", false);
        } else if (model.containsAttribute("isUsernameUnique")) {
            model.addAttribute("isUsernameUnique", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRepeatPass())) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:register";
        }

        if (isEmailUnique(userRegisterBindingModel, redirectAttributes)) {
            return "redirect:register";
        }

        if (isUsernameUnique(userRegisterBindingModel, redirectAttributes)) {
            return "redirect:register";
        }

        UserServiceModel newUser = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        userService.registerUserAndLogin(newUser);
        return "redirect:/";
    }

    @Transactional
    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        UserViewModel userViewModel = modelMapper.map(userService.findByEmail(principal.getName()), UserViewModel.class);
        model.addAttribute("profile", userViewModel);
        return "profile";
    }


    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        UserViewModel userViewModel = modelMapper.map(userService.findById(id), UserViewModel.class);
        model.addAttribute("profile", userViewModel);
        return "profile";
    }

    private boolean isUsernameUnique(UserRegisterBindingModel userRegisterBindingModel, RedirectAttributes redirectAttributes) {
        if (userService.usernameExists(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsernameUnique", true);
            return true;
        }
        return false;
    }

    private boolean isEmailUnique(UserRegisterBindingModel userRegisterBindingModel, RedirectAttributes redirectAttributes) {
        if (userService.emailExists(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isEmailUnique", true);
            return true;
        }
        return false;
    }

    @ModelAttribute("userModel")
    public UserRegisterBindingModel userModel() {
        return new UserRegisterBindingModel();
    }
}
