package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String updateFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "File not selected to upload");

            return "redirect:/result";
        }

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        if (!fileService.isFilenameAvailable(multipartFile.getOriginalFilename(), userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "There is a file with the same name");

            return "redirect:/result";
        }

        try {
            fileService.insert(multipartFile, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Your file was uploaded successfully.");
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the file insert. Please try again!");
        }

        return "redirect:/result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") Integer fileId, RedirectAttributes redirectAttributes) {

        try {
            fileService.delete(fileId);
            redirectAttributes.addFlashAttribute("successMessage", "Your note was deleted successfully.");
        } catch (Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the file delete. Please try again!");
        }

        return "redirect:/result";
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId) {
        File file = fileService.getFile(fileId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok().headers(httpHeaders).body(resource);
    }

}
