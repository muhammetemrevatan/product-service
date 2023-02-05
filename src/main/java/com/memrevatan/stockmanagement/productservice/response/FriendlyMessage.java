package com.memrevatan.stockmanagement.productservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendlyMessage {
    private String title; // error, success gibi message title alacak.
    private String description; // product not created , product successfuly created -> gibi hatanın veya başarılı olan olayın açıklamasını içerecek.
    private String buttonPositive; // aslında bu alan ui tarafında kullanılacak ama simdiden tanılıyoruz. butonun durumunu belirleyecek.

}
