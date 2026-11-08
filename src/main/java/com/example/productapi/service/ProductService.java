package com.example.productapi.service;

import com.example.productapi.dto.ProductResponse;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse createProduct(ProductResponse request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        Product saved = repository.save(product);
        return mapToResponse(saved);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
    }
    // Récupérer tous les produits (getAll)
    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // Mettre à jour un produit (update)
    public ProductResponse updateProduct(Long id, ProductResponse request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec l'id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product updated = repository.save(product);
        return mapToResponse(updated);
    }
    // Supprimer un produit (delete)
    public void deleteProduct(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec l'id: " + id));
        repository.deleteById(id);
    }
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec l'id: " + id));
        return mapToResponse(product);
    }
}