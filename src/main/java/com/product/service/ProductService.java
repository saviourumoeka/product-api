package com.product.service;

import com.product.dto.CreateProductDTO;
import com.product.dto.ProductAmountDTO;
import com.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductDTO createProductDTO);

    ProductDTO  getProduct(Long id);

    ProductAmountDTO getProductAmount(Long id);

    List<ProductDTO > getAllProducts();

    ProductDTO  updateProduct(Long id, CreateProductDTO createProductDTO);

    void deleteProduct(Long id);
}
