package org.tolking.animeharbor.service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.web.multipart.MultipartFile;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface ImageService {
    void saveProfile(MultipartFile file, String username) throws IOFileUploadException, InvalidMimeTypeException;

    void saveAnimePic(MultipartFile file, long animeId) throws IOFileUploadException, InvalidMimeTypeException;

    ImageDataDto load(long id) throws FileNotFoundException;

    void delete(Image image);

    Optional<Image> findImageByName(String name);

}