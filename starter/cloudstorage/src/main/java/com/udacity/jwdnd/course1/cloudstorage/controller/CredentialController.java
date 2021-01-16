package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
@RequestMapping("/credentials")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String createOrUpdateCredential(Authentication authentication, Credential credential, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        if (credential.getCredentialId() != null) {

            try {
                credentialService.update(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was updated successfully.");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credential update. Please try again!");
            }
        } else {

            try {
                credentialService.insert(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was created successfully.");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credential insert. Please try again!");
            }
        }

        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(value = "credentialId") Integer credentialId, RedirectAttributes redirectAttributes) {

        if (credentialId > 0) {
            try {
                credentialService.delete(credentialId);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was deleted successfully.");
                return "redirect:/result";
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credential delete. Please try again!");
                return "redirect:/result";
            }
        }

        return "redirect:/home";
    }
}
