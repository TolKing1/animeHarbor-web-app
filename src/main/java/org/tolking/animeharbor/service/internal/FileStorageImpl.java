package org.tolking.animeharbor.service.internal;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.FilesStorageService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageImpl implements FilesStorageService {
    private static final String DEFAULT_PICTURE = "default.jpg";
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    private final Path rootProfile = Path.of("images", "profile");
    private final Path rootAnime = Path.of("images", "anime");

    private final UserRepository userRepository;

    public FileStorageImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        createDirectory(rootProfile);
        createDirectory(rootAnime);
    }


    @Override
    public void saveProfile(MultipartFile file, String username) throws IOFileUploadException, InvalidMimeTypeException {
        saveFile(file, rootProfile, username);
    }

    @Override
    public void saveAnime(MultipartFile file, String username) throws IOFileUploadException,InvalidMimeTypeException  {
        saveFile(file, rootAnime, username);
    }

    private void saveFile(MultipartFile file, Path rootPath, String username) throws IOFileUploadException, InvalidMimeTypeException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String fileExtension = getFileExtension(file.getOriginalFilename());
            assert fileExtension != null;
            if (!isValidFileType(fileExtension)) {
                throw new InvalidMimeTypeException("File type not supported:"+fileExtension);
            }

            String newFileName = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
            File newFile = rootPath.resolve(newFileName).toFile();

            try {
                Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                throw new IOFileUploadException("Can't save file",e);
            }

            if (!user.getPicture().equals(DEFAULT_PICTURE)) {
                FileUtils.deleteQuietly(rootPath.resolve(user.getPicture()).toFile());
            }

            user.setPicture(newFileName);
            userRepository.save(user);
        };
    }

    @Override
    public ImageDataDto loadFromProfile(String filename) throws FileNotFoundException {
        return loadImage(filename, rootProfile);
    }

    @Override
    public ImageDataDto loadFromAnime(String filename) throws FileNotFoundException {
        return loadImage(filename, rootAnime);
    }

    private ImageDataDto loadImage(String filename, Path rootPath) throws FileNotFoundException {
        File file = rootPath.resolve(filename).toFile();

        byte[] data = null;
        try {
            data = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new FileNotFoundException("File Not Found");
        }
        String mimeType = StringUtils.getFilenameExtension(filename);
        return new ImageDataDto(new ByteArrayResource(data), mimeType);
    }

    private String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    private void createDirectory(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder: " + directory, e);
        }
    }

    private boolean isValidFileType(String fileExtension) {
        return Arrays.asList(ALLOWED_EXTENSIONS).contains(fileExtension.toLowerCase());
    }


}
