package com.webproject.studentregister.service;

import com.webproject.studentregister.model.Image;
import com.webproject.studentregister.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image save(MultipartFile image){
        try {
            Image img = new Image();
            String s = Base64.getEncoder().encodeToString(image.getBytes());
            img.setImageData(s);
            img.setName(UUID.randomUUID().toString());
            img.setType(image.getContentType());
            return imageRepository.save(img);
        } catch (IOException e) {
            return null;
        }
    }

    public void delete(String imageName) {
        imageRepository.deleteByName(imageName);
    }

    public String viewImage(String imageName){
        String data =imageRepository.getByName(imageName).getImageData();
        return data;
    }
}
