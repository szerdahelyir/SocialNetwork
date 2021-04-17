package com.example.demo.controller;

import com.example.demo.models.Image;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public void uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        imageService.uploadImage(file);
    }

    @GetMapping(path = {"/get/{imageId}"})
    public Image getImage(@PathVariable("imageId") Long imageId) throws IOException {
        return imageService.getImage(imageId);
    }

}
