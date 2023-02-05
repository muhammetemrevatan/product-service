package com.memrevatan.stockmanagement.productservice.controller;

import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.memrevatan.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.memrevatan.stockmanagement.productservice.repository.entity.Product;
import com.memrevatan.stockmanagement.productservice.request.ProductCreateRequest;
import com.memrevatan.stockmanagement.productservice.request.ProductGetRequest;
import com.memrevatan.stockmanagement.productservice.response.FriendlyMessage;
import com.memrevatan.stockmanagement.productservice.response.InternalApiResponse;
import com.memrevatan.stockmanagement.productservice.response.ProductResponse;
import com.memrevatan.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // RestFull web service olarak işlev gördüğünü belirtmek için kullanılır. // TYPE JSON OLUYOR request ve response json olarak belirleniyor. // REST yerine SOAP kullanılsaydı XML falan olabilir bir araştır bunu.
@RequestMapping(value = "/api/1.0/product") // belirli bir url için yapılan istegi bir controller'a baglamak icin kullanabiliriz. Eger uygulama monolith olsaydı birden fazla controller olabilirdi ve her controller için farkılı requestmappingler verirdik.
@RequiredArgsConstructor
class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED) // Durum kodu olarak 201 döndürmesi lazım cünkü kaydetme islemini (CREATED = 201) dogru cevap dönmesi adına bunu yazıyoruz.
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language")Language language, @RequestBody ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language,productCreateRequest); // Bu asamada request'i veritabanina kaydettik.
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/productId")
    public InternalApiResponse<?> getProduct(@PathVariable("language") Language language, @RequestParam(value = "productId") Long productId) {
        log.debug("[{}][getProduct] -> request: id={}", this.getClass().getSimpleName(), productId);
        Product product = productRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_GET))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private ProductResponse convertProductResponse(Product product) {
        return ProductResponse
                .builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .quantity(product.getQuantity())
                    .price(product.getPrice())
                    .productCreatedDate(product.getProductCreatedDate().getTime())
                    .productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }
}
