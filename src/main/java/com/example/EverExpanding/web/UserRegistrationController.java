package com.example.EverExpanding.web;

import com.example.EverExpanding.model.binding.UserRegisterBindingModel;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRegistrationController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if(model.containsAttribute("isEmailUnique")) {
            model.addAttribute("isEmailUnique", false);
        } else if(model.containsAttribute("isUsernameUnique")) {
            model.addAttribute("isUsernameUnique", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getRepeatPass())) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:register";
        }

        if(isEmailUnique(userRegisterBindingModel, redirectAttributes)) {
            return "redirect:register";
        }

        if(isUsernameUnique(userRegisterBindingModel, redirectAttributes)) {
            return "redirect:register";
        }

        UserServiceModel newUser = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        userService.registerUserAndLogin(newUser);
        return "redirect:/";
    }

    private boolean isUsernameUnique(UserRegisterBindingModel userRegisterBindingModel, RedirectAttributes redirectAttributes) {
        if(userService.usernameExists(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsernameUnique", true);
            return true;
        }
        return false;
    }

    private boolean isEmailUnique(UserRegisterBindingModel userRegisterBindingModel, RedirectAttributes redirectAttributes) {
        if(userService.emailExists(userRegisterBindingModel.getEmail())) {
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
