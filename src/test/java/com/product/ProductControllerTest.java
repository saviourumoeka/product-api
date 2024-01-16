package com.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.controller.ProductController;
import com.product.dto.CreateProductDTO;
import com.product.dto.ProductDTO;
import com.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void createProduct_Success() throws Exception {

        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setDescription("Test Product");
        createProductDTO.setAmount(BigDecimal.TEN);
        createProductDTO.setActive(true);

        ProductDTO createdProduct = new ProductDTO(1L, "Test Product", BigDecimal.TEN, true, LocalDateTime.now());

        when(productService.createProduct(any(CreateProductDTO.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Test Product"))
                .andExpect(jsonPath("$.amount").value(10.0))
                .andExpect(jsonPath("$.active").value(true));

        verify(productService, times(1)).createProduct(any(CreateProductDTO.class));
    }

    @Test
    void getProduct_Success() throws Exception {
        long productId = 1L;
        ProductDTO productDTO = new ProductDTO(productId, "Existing Product", BigDecimal.valueOf(20.0), true, LocalDateTime.now());

        when(productService.getProduct(productId)).thenReturn(productDTO);

        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Existing Product"))
                .andExpect(jsonPath("$.amount").value(20.0))
                .andExpect(jsonPath("$.active").value(true));

        verify(productService, times(1)).getProduct(productId);
    }
    @Test
    void getAllProducts_Success() throws Exception {
        ProductDTO product1 = new ProductDTO(1L, "Test Product 1", BigDecimal.valueOf(30.0), true, LocalDateTime.now());
        ProductDTO product2 = new ProductDTO(2L, "Test Product 2", BigDecimal.valueOf(40.0), false, LocalDateTime.now());

        List<ProductDTO> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test Product 1"))
                .andExpect(jsonPath("$[0].amount").value(30.0))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].description").value("Test Product 2"))
                .andExpect(jsonPath("$[1].amount").value(40.0))
                .andExpect(jsonPath("$[1].active").value(false));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void updateProduct_Success() throws Exception {
        long productId = 1L;
        CreateProductDTO updateProductDTO = new CreateProductDTO();
        updateProductDTO.setDescription("Updated Product");
        updateProductDTO.setAmount(BigDecimal.valueOf(50.0));
        updateProductDTO.setActive(false);

        ProductDTO updatedProduct = new ProductDTO(productId, "Updated Product", BigDecimal.valueOf(50.0), false, LocalDateTime.now());

        when(productService.updateProduct(eq(productId), any(CreateProductDTO.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Product"))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.active").value(false));

        verify(productService, times(1)).updateProduct(eq(productId), any(CreateProductDTO.class));
    }

    @Test
    void deleteProduct_Success() throws Exception {

        long productId = 1L;

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
    }
}
