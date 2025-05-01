package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final ImageService imageService;

    @PostMapping("image/{productId}")
    public ResponseEntity<?> uploadProductImage(@PathVariable("productId") String id, @RequestParam("file") MultipartFile file) {
        imageService.updateProductImage(id, file);
        return ResponseEntity.accepted().build();
    }
}
