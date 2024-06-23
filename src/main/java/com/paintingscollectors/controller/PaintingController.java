package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDto;
import com.paintingscollectors.model.enums.StyleName;
import com.paintingscollectors.service.PaintingServiceImpl;
import com.paintingscollectors.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PaintingController {

    private final PaintingServiceImpl paintingServiceImpl;

    private final UserSession userSession;
    private final UserServiceImpl userServiceImpl;

    public PaintingController(PaintingServiceImpl paintingServiceImpl, UserSession userSession, UserServiceImpl userServiceImpl) {
        this.paintingServiceImpl = paintingServiceImpl;
        this.userSession = userSession;
        this.userServiceImpl = userServiceImpl;
    }

    @ModelAttribute("paintingData")
    public AddPaintingDto addPaintingDto() {
        return new AddPaintingDto();
    }


    @GetMapping("/paintings/add-painting")
    public String viewAddPainting(Model model){
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        model.addAttribute("styleOptions", StyleName.values());
        return "add-painting";
    }

    @PostMapping("/paintings/add-painting")
    public String doAddPainting(
            @Valid AddPaintingDto addPaintingDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("paintingData", addPaintingDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);
            return "redirect:/paintings/add-painting";
        }

        if (!paintingServiceImpl.addNewPainting(addPaintingDto)) {
            redirectAttributes.addFlashAttribute("paintingData", addPaintingDto);
            redirectAttributes.addFlashAttribute("somethingWrong", true);
            return "redirect:/paintings/add-painting";
        }

        return "redirect:/home";
    }

    @GetMapping("/paintings/remove-painting/{id}")
    public String removePainting(@PathVariable long id, RedirectAttributes redirectAttributes) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        if (!paintingServiceImpl.removePainting(id)) {
            redirectAttributes.addFlashAttribute("paintingIsInFavorite", true);
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    @GetMapping("/paintings/add-favorite/{id}")
    public String addFavoritePainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        paintingServiceImpl.addFavorite(id);
        return "redirect:/home";
    }

    @GetMapping("/paintings/vote/{id}")
    public String voteForPainting(@PathVariable long id, RedirectAttributes redirectAttributes) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        if (!paintingServiceImpl.voteForPainting(id)) {
            redirectAttributes.addFlashAttribute("alreadyVoted", true);
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    @GetMapping("/paintings/remove-favorite/{id}")
    public String removeFavoritePainting(@PathVariable long id) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userServiceImpl.removeFavorite(id);
        return "redirect:/home";
    }






}
