package com.memrevatan.stockmanagement.productservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Long productCreatedDate; // gelen cevapta get metodu kullanıcaz normalde Date olarak tanımladık ama muhtemelen ms olarak karsilanicak.
    private Long productUpdatedDate; // gelen cevapta get metodu kullanıcaz normalde Date olarak tanımladık ama muhtemelen ms olarak karsilanicak.
}

