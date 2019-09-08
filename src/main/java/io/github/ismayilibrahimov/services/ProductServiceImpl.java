package io.github.ismayilibrahimov.services;

import io.github.ismayilibrahimov.config.StorageProperties;
import io.github.ismayilibrahimov.models.Product;
import io.github.ismayilibrahimov.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final Path rootLocation;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(StorageProperties properties, ProductRepository productRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.productRepository = productRepository;
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            System.out.println("Could not initialize storage location " + e);
        }
    }



    @Override
    public void uploadImage(MultipartFile image, String imageName) throws Exception {
        String filename = StringUtils.cleanPath(imageName);
        try {
            if (image.isEmpty()) {
                throw new Exception("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new Exception(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new IOException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void deleteImage(String imageName) throws IOException {
        String filename = StringUtils.cleanPath(imageName);
        Files.delete(this.rootLocation.resolve(filename));
    }

    @Override
    public void save(Product product, MultipartFile image) {
        long time = System.currentTimeMillis();
        String imageName = time + image.getOriginalFilename();
        try {
            this.uploadImage(image, imageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Product productModel = new Product();
        productModel.setTitle(product.getTitle());
        productModel.setDescription(product.getDescription());
        productModel.setImageName(imageName);
        productRepository.save(productModel);
    }


    @Override
    public void update(Product product, MultipartFile image) {
        if (!image.isEmpty()) {
            long time = System.currentTimeMillis();
            String imageName = time + image.getOriginalFilename();
            product.setImageName(imageName);
            try {
                this.uploadImage(image, imageName);
                this.deleteImage(productRepository.findById(product.getId()).get().getImageName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            product.setImageName(productRepository.findById(product.getId()).get().getImageName());
        }
        productRepository.save(product);
    }


    @Override
    public void delete(long id) {
        Product product = productRepository.findById(id).get();

        try {
            deleteImage(product.getImageName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        productRepository.delete(product);
    }
}
