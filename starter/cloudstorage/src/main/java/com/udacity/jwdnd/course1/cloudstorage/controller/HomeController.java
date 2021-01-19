package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Note note, Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        // Files
        model.addAttribute("files", fileService.getFiles(userId));

        // Notes
        model.addAttribute("notes", noteService.getNotes(userId));
        model.addAttribute("note", note);

        // Credentials
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("credential", credential);
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

}
