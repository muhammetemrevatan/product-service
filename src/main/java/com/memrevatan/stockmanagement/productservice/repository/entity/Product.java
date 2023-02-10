package com.memrevatan.stockmanagement.productservice.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Product Api model documentation", description = "Model")
public class Product {

    @Id // PK ozelligini field icin ekler
    @Column(name = "product_id") // tablo'da column buraya yazdigimiz ismi alacak.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1 den baslayip artarak devam etsin. PK icin deger atanmasindan database sorumlu olacak
    @ApiModelProperty(value = "Unique id field of product object")
    private long productId;

    @Column(name = "product_name")
    @ApiModelProperty(value = "Name field of product object")
    private String productName;

    @Column(name = "quantity")
    @ApiModelProperty(value = "Quantity field of product object")
    private int quantity;

    @Column(name = "price")
    @ApiModelProperty(value = "Price field of product object")
    private double price;

    @Builder.Default // constructerde default olarak her zaman parametre olarak geçecek demek. Yani ben .buildir().productid .... seklinde yazdigimda bu alanı yazmasam bile default olarak alacak.
    @Column(name = "product_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Update date field of product object")
    private Date productUpdatedDate = new Date();

    @Builder.Default
    @Column(name = "product_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "Create date field of product object")
    private Date productCreatedDate = new Date();

    @Column(name = "is_deleted")
    @ApiModelProperty(value = "Delete field of product object")
    private boolean deleted; // soft delete yapmak icin ekledik gercekten bisey silmicez. true false ile kontrol edecegiz

}
