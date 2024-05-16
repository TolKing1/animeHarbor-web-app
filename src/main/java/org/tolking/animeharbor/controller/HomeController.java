package org.tolking.animeharbor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.service.AnimeService;
import org.tolking.animeharbor.service.FilesStorageService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AnimeService animeService;
    private final FilesStorageService storageService;
    @GetMapping({"/", ""})
    public String home(Model model) {
        model.addAttribute("animeByPopular",animeService.getAllForPopularityPage());
        model.addAttribute("animeByView",animeService.getAllForTopViewPage());
        model.addAttribute("animeByInsertDate",animeService.getAllForRecentlyAddedPage());
        return "index";
    }

    @GetMapping("/images/profile/{filename:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) throws IOException {
        ImageDataDto imageData = storageService.loadFromProfile(filename);

        ByteArrayResource imageByte = imageData.getResource();
        String mimeType = imageData.getMimeType();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(imageByte.contentLength())
                .contentType(MediaType.valueOf("image/"+mimeType))
                .body(imageByte);
    }
}
