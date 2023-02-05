package com.memrevatan.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductCreateRequest {
    // bir kayit eklerden id almamıza gerek yok identity yaptık database otomatik atıyor.
    private String productName;
    private Integer quantity;
    private Double price;


}
