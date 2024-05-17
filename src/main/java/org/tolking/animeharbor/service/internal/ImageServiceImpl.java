package org.tolking.animeharbor.service.internal;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.tolking.animeharbor.dto.ImageDataDto;
import org.tolking.animeharbor.entities.Image;
import org.tolking.animeharbor.entities.User;
import org.tolking.animeharbor.entities.enums.ImageType;
import org.tolking.animeharbor.exception.InvalidMimeTypeException;
import org.tolking.animeharbor.repositories.ImageRepository;
import org.tolking.animeharbor.repositories.UserRepository;
import org.tolking.animeharbor.service.ImageService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.tolking.animeharbor.service.seeder.ImageSeeder.DEFAULT_PROFILE_IMG;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public void saveProfile(MultipartFile file, String username) throws IOFileUploadException, InvalidMimeTypeException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Image image = user.getImage();
            String extension = getFileExtension(file.getOriginalFilename());

            image = createNewIfDefault(image);
            isValidFileType(extension);

            extension = validateJPG(extension);
            try {
                image.setData(getImageString(file));
                image.setFilename(UUID.randomUUID() + "." + extension);

                image = imageRepository.save(image);

                user.setImage(image);
            }catch (IOException e){
                throw new IOFileUploadException("Can't save file",e);
            }

            userRepository.save(user);
        }
    }

    @Override
    public ImageDataDto load(long id) throws FileNotFoundException {
        Optional<Image> image = imageRepository.findById(id);

        if (image.isPresent()) {
            Image imageData = image.get();
            String mimeType = StringUtils.getFilenameExtension(imageData.getFilename());
            // Response contentType doesn't support jpg
            mimeType = validateJPG(mimeType);

            ByteArrayResource resource = new ByteArrayResource(Base64.getDecoder().decode(imageData.getData()));

            return new ImageDataDto(resource , mimeType);
        }else {
            throw new FileNotFoundException("Image not found");
        }
    }

    @Override
    public Optional<Image> findImageByName(String name) {
        return imageRepository.findByFilename(name);
    }

    private String getImageString(MultipartFile multipartFile) throws IOException {

        try {
            byte[] imageBytes = IOUtils.toByteArray( multipartFile.getInputStream());
            return  Base64.getEncoder().encodeToString(imageBytes);
        }catch (Exception e) {
            throw new IOException("Can't read image string");
        }
    }

    private static Image createNewIfDefault(Image image) {
        if (image.getFilename().equals(DEFAULT_PROFILE_IMG)){
            image = new Image();
            image.setImageType(ImageType.PROFILE);
        }
        return image;
    }

    private String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    private static String validateJPG(String mimeType) {
        if (Objects.equals(mimeType, "jpg")) {
            mimeType = "jpeg";
        }
        return mimeType;
    }

    private void isValidFileType(String fileExtension) throws InvalidMimeTypeException {
        try {
            if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(fileExtension.toLowerCase())){
                throw new Exception();
            }
        }catch (Exception e){
            throw new InvalidMimeTypeException("File type not supported:"+fileExtension);
        }
    }
}
