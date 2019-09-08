package io.github.ismayilibrahimov.repositories;

import io.github.ismayilibrahimov.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }
