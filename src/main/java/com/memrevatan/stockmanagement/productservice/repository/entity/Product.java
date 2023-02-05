package com.memrevatan.stockmanagement.productservice.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data // lombok dependency den geliyor - toString, equalsAndHashCode, getter, setter, RequiredArgsConstructor --> hepsini icinde barindirir.
@Builder // lombok dependency den geliyor - Builder pattern kullanmamıza olanak sagliyor.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "stock_management")
public class Product {

    @Id // PK ozelligini field icin ekler
    @Column(name = "product_id") // tablo'da column buraya yazdigimiz ismi alacak.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1 den baslayip artarak devam etsin. PK icin deger atanmasindan database sorumlu olacak
    private long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Builder.Default // constructerde default olarak her zaman parametre olarak geçecek demek. Yani ben .buildir().productid .... seklinde yazdigimda bu alanı yazmasam bile default olarak alacak.
    @Column(name = "product_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productUpdatedDate = new Date();

    @Builder.Default
    @Column(name = "product_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productCreatedDate = new Date();

    @Column(name = "is_deleted")
    private boolean deleted; // soft delete yapmak icin ekledik gercekten bisey silmicez. true false ile kontrol edecegiz

}
