package io.github.ismayilibrahimov.services;

import io.github.ismayilibrahimov.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;


public interface ProductService {
    void init();
    void uploadImage(MultipartFile image, String imageName) throws Exception;
    void deleteImage(String imageName) throws IOException;
    void save(Product product, MultipartFile image);
    void update(Product product, MultipartFile image);
    void delete(long id);
}
