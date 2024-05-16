package org.tolking.animeharbor.service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.web.multipart.MultipartFile;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface ImageService {
  public void saveProfile(MultipartFile file, String username) throws IOFileUploadException, InvalidMimeTypeException;

  public ImageDataDto load(long id) throws FileNotFoundException;

  public Optional<Image> findImageByName(String name);

}