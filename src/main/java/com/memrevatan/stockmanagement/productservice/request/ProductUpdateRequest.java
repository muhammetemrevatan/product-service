package com.memrevatan.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long productId; // bir kaydı güncellestirirken id sine ihtiyacımız olacak
    private String productName;
    private Integer quantity;
    private Double price;
}
