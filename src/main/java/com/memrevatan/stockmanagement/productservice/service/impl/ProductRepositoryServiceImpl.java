package com.memrevatan.stockmanagement.productservice.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.memrevatan.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import com.memrevatan.stockmanagement.productservice.exception.exceptions.ProductNotFoundException;
import com.memrevatan.stockmanagement.productservice.repository.ProductRepository;
import com.memrevatan.stockmanagement.productservice.repository.entity.Product;
import com.memrevatan.stockmanagement.productservice.request.ProductCreateRequest;
import com.memrevatan.stockmanagement.productservice.request.ProductGetRequest;
import com.memrevatan.stockmanagement.productservice.request.ProductUpdateRequest;
import com.memrevatan.stockmanagement.productservice.response.ProductResponse;
import com.memrevatan.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j // tries , debug, info, warn, error
@Service // business logic'lerinin var olduğunu belli etmek için ekledik.
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product productResponse = productRepository.save(product);
            log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (Exception exception) {
            throw new ProductNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "product request: " + productCreateRequest.toString());
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][getProduct] -> request: {}", this.getClass().getSimpleName(), productId);
        Product productResponse = productRepository.findFirstByProductIdAndDeletedFalse(productId);
        if (Objects.isNull(productResponse)) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "product request: gets product" + productId);
        }
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), productId);
        return productResponse;
    }

    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}][getProducts] -> request: {}", this.getClass().getSimpleName(), "gets product");
        List<Product> getAllProductResponse = productRepository.getAllByDeletedFalse();
        if (Objects.isNull(getAllProductResponse)) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "products request: get products");
        }
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), "gets product successfully");
        return getAllProductResponse;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productUpdateRequest);
        Product product = getProduct(language, productId);
        product.setProductName(productUpdateRequest.getProductName());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setPrice(productUpdateRequest.getPrice());
        product.setProductCreatedDate(product.getProductCreatedDate());
        product.setProductUpdatedDate(new Date());
        Product productResponse =  productRepository.save(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        return null;
    }
}

