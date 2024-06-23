package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDto;
import com.paintingscollectors.model.dto.UserRegisterDto;
import com.paintingscollectors.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final UserSession userSession;

    public UserController(UserServiceImpl userServiceImpl, UserSession userSession) {
        this.userServiceImpl = userServiceImpl;
        this.userSession = userSession;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("loginData")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }

    @GetMapping("/users/register")
    public String viewRegister() {
        if (userSession.isLoggedIn()) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/users/register")
    public String doRegister(
            @Valid UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/users/register";
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
            return "redirect:/users/register";
        }

        if (!userServiceImpl.register(userRegisterDto)) {
            redirectAttributes.addFlashAttribute("registerData", userRegisterDto);
            redirectAttributes.addFlashAttribute("alreadyInUse", true);
            return "redirect:/users/register";
        }

        return  "redirect:/users/login";
    }

    @GetMapping("/users/login")
    public String viewLogin() {
        if (userSession.isLoggedIn()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/users/login")
    public String doLogin(
            @Valid UserLoginDto userLoginDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/users/login";
        }

        if (!userServiceImpl.login(userLoginDto)) {
            redirectAttributes.addFlashAttribute("loginData", userLoginDto);
            redirectAttributes.addFlashAttribute("wrongCredentials", true);
            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/users/logout")
    public String logout() {
        userSession.logout();
        return "redirect:/";
    }

}
