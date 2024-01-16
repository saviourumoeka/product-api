package com.product.service.serviceImpl;

import com.product.dto.CreateProductDTO;
import com.product.dto.ProductAmountDTO;
import com.product.dto.ProductDTO;
import com.product.persistence.entity.Product;
import com.product.persistence.repository.ProductRepository;
import com.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(CreateProductDTO createProductDTO) {

        Product product = new Product();
        product.setActive(createProductDTO.getActive());
        product.setDescription(createProductDTO.getDescription());
        product.setAmount(createProductDTO.getAmount());

        Product savedProduct = productRepository.save(product);

        return mapProductToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return mapProductToProductDTO(product);
    }

    @Override
    public ProductAmountDTO getProductAmount(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return mapProductToProductAmountDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapProductToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long id, CreateProductDTO createProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setDescription(createProductDTO.getDescription());
        product.setAmount(createProductDTO.getAmount());
        product.setActive(createProductDTO.getActive());

        Product updatedProduct = productRepository.save(product);

        return mapProductToProductDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapProductToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getDescription(),
                product.getAmount(),
                product.getActive(),
                product.getCreationDate()
        );
    }

    private ProductAmountDTO mapProductToProductAmountDTO(Product product) {
        return new ProductAmountDTO(
                product.getAmount()
        );
    }
}
