package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingInfoDto;
import com.paintingscollectors.service.PaintingServiceImpl;
import com.paintingscollectors.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final UserServiceImpl userServiceImpl;
    private final PaintingServiceImpl paintingServiceImpl;

    public HomeController(UserSession userSession, UserServiceImpl userServiceImpl, PaintingServiceImpl paintingServiceImpl) {
        this.userSession = userSession;
        this.userServiceImpl = userServiceImpl;
        this.paintingServiceImpl = paintingServiceImpl;
    }

    @GetMapping("/")
    public String notLoggedIn() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }


    @GetMapping("/home")
    public String loggedIn(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        Set<PaintingInfoDto> currentUserPaintings = userServiceImpl.getCurrentUserPaintings();
        Set<PaintingInfoDto> allOtherPaintings = paintingServiceImpl.getAllOtherPaintings();
        Set<PaintingInfoDto> favoritePaintings = userServiceImpl.getFavoritePaintings();
        LinkedHashSet<PaintingInfoDto> mostVotedPaintings = paintingServiceImpl.getMostVotedPaintings();

        System.out.println();

        model.addAttribute("currentUserPaintings", currentUserPaintings);
        model.addAttribute("allOtherPaintings", allOtherPaintings);
        model.addAttribute("favoritePaintings", favoritePaintings);
        model.addAttribute("mostVotedPaintings", mostVotedPaintings);

        return "home";
    }
}
