package io.github.ismayilibrahimov.controllers;

import io.github.ismayilibrahimov.models.Product;
import io.github.ismayilibrahimov.models.onCreate;
import io.github.ismayilibrahimov.models.onUpdate;
import io.github.ismayilibrahimov.repositories.ProductRepository;
import io.github.ismayilibrahimov.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @GetMapping("/")
    public String home(Model model)
    {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/content/{id}")
    public String content(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("product", productRepository.findById(id).get());
        return "content";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }


    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("product", new Product());
        return "create";
    }


    @PostMapping("/store")
    public String store(@Validated(onCreate.class) @ModelAttribute Product product,
                        BindingResult result,
                        @RequestParam("image") MultipartFile image)
    {
        if (result.hasErrors()) {
            return "create";
        }
        productService.save(product, image);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model)
    {
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product", product.get());
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String update(@Validated(onUpdate.class) @ModelAttribute Product product,
                         BindingResult result,
                         @RequestParam("image") MultipartFile image)
    {

        if (result.hasErrors()) {
            return "edit";
        }

        productService.update(product, image);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        productService.delete(id);
        return "redirect:/";
    }




}
