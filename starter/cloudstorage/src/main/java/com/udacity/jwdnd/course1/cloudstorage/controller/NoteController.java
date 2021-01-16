package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String createOrUpdateNote(Authentication authentication, Note note, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);

        if (note.getNoteId() != null) {

            try {
                noteService.update(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was updated successfully.");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
            }
        } else {

            try {
                noteService.insert(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was created successfully.");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note insert. Please try again!");
            }
        }

        return "redirect:/result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable(value = "noteId") Integer noteId, RedirectAttributes redirectAttributes) {

        if (noteId > 0) {
            try {
                noteService.delete(noteId);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was deleted successfully.");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note delete. Please try again!");
                return "redirect:/result";
            }
        }

        return "redirect:/home";
    }
}