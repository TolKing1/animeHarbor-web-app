package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.service.ImageService;

import java.io.IOException;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable long id) throws IOException {
        ImageDataDto imageData = imageService.load(id);

        ByteArrayResource imageByte = imageData.resource();
        String mimeType = imageData.mimeType();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(imageByte.contentLength())
                .contentType(MediaType.valueOf("image/"+mimeType))
                .body(imageByte);
    }
}
