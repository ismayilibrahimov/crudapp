package io.github.ismayilibrahimov.models;

import io.github.ismayilibrahimov.validation.NotEmptyImage;
import io.github.ismayilibrahimov.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(groups = {onCreate.class, onUpdate.class})
    @Size(min = 3, max = 100, groups = {onCreate.class, onUpdate.class})
    private String title;
    @NotBlank(groups = {onCreate.class, onUpdate.class})
    @Size(min = 3, max = 500, groups = {onCreate.class, onUpdate.class})
    private String description;
    @Transient
    @ValidImage(groups = {onCreate.class, onUpdate.class})
    @NotEmptyImage(groups = onCreate.class)
    private MultipartFile image;
    @Column(name = "image")
    private String imageName;

    public Product(){}

    public Product(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
