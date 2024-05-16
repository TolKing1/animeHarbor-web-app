package org.tolking.animeharbor.service;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.web.multipart.MultipartFile;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;

import java.io.FileNotFoundException;

public interface FilesStorageService {
  public void saveProfile(MultipartFile file, String username) throws IOFileUploadException, InvalidMimeTypeException;
  public void saveAnime(MultipartFile file, String username) throws IOFileUploadException,InvalidMimeTypeException ;

  public ImageDataDto loadFromProfile(String filename) throws FileNotFoundException;
  public ImageDataDto loadFromAnime(String filename) throws FileNotFoundException;

}