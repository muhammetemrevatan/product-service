package com.memrevatan.stockmanagement.productservice.controller;

import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.memrevatan.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.memrevatan.stockmanagement.productservice.repository.entity.Product;
import com.memrevatan.stockmanagement.productservice.request.ProductCreateRequest;
import com.memrevatan.stockmanagement.productservice.request.ProductUpdateRequest;
import com.memrevatan.stockmanagement.productservice.response.FriendlyMessage;
import com.memrevatan.stockmanagement.productservice.response.InternalApiResponse;
import com.memrevatan.stockmanagement.productservice.response.ProductResponse;
import com.memrevatan.stockmanagement.productservice.service.IProductRepositoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
// RestFull web service olarak işlev gördüğünü belirtmek için kullanılır. // TYPE JSON OLUYOR request ve response json olarak belirleniyor. // REST yerine SOAP kullanılsaydı XML falan olabilir bir araştır bunu.
@RequestMapping(value = "/api/1.0/product")
// belirli bir url için yapılan istegi bir controller'a baglamak icin kullanabiliriz. Eger uygulama monolith olsaydı birden fazla controller olabilirdi ve her controller için farklı requestmappingler verirdik.
@RequiredArgsConstructor
@Api(value = "Product Api Documentation")
class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ApiOperation(value = "This method creates the product")
    @ResponseStatus(HttpStatus.CREATED) // Durum kodu olarak 201 döndürmesi lazım cünkü kaydetme islemini (CREATED = 201) dogru cevap dönmesi adına bunu yazıyoruz.
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language") Language language, @RequestBody ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language, productCreateRequest); // Bu asamada request'i veritabanina kaydettik.
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse> builder().friendlyMessage(FriendlyMessage.builder().title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED)).build()).httpStatus(HttpStatus.CREATED).hasError(false)
                .payload(productResponse).build();
    }

    @ApiOperation(value = "This method gets the product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/productId")
    public InternalApiResponse<ProductResponse> getProduct(@PathVariable("language") Language language, @RequestParam(value = "productId") Long productId) {
        log.debug("[{}][getProduct] -> request: id={}", this.getClass().getSimpleName(), productId);
        Product product = productRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse> builder().friendlyMessage(FriendlyMessage.builder().title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_GET)).build()).httpStatus(HttpStatus.OK).hasError(false)
                .payload(productResponse).build();
    }

    @ApiOperation(value = "This method get the products")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}")
    public InternalApiResponse<List<ProductResponse>> getProducts(@PathVariable("language") Language language) {
        log.debug("[{}][getProducts] -> request: getAllProduct - no context", this.getClass().getSimpleName());
        List<Product> products = productRepositoryService.getProducts(language);
        List<ProductResponse> productResponses = convertProductsResponse(products);
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), productResponses);
        return InternalApiResponse.<List<ProductResponse>> builder().friendlyMessage(FriendlyMessage.builder().title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_GET)).build()).httpStatus(HttpStatus.OK).hasError(false)
                .payload(productResponses).build();
    }

    @ApiOperation(value = "This method updates the product")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{language}/update/productId")
    public InternalApiResponse<ProductResponse> updateProduct(@PathVariable(value = "language") Language language, @RequestParam(value = "productId") Long productId,
            @RequestBody ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productUpdateRequest);
        Product product = productRepositoryService.updateProduct(language, productId, productUpdateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productUpdateRequest);
        return InternalApiResponse.<ProductResponse> builder().friendlyMessage(FriendlyMessage.builder().title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATE)).build()).httpStatus(HttpStatus.OK).hasError(false)
                .payload(productResponse).build();
    }

    @ApiOperation(value = "This method deleted the product")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{language}/delete/productId")
    public InternalApiResponse<ProductResponse> deleteProduct(@PathVariable(value = "language") Language language, @RequestParam(value = "productId") Long productId) {
        log.debug("[{}][updateProduct] -> request: productId {}", this.getClass().getSimpleName(), productId);
        Product product = productRepositoryService.deleteProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse> builder().friendlyMessage(FriendlyMessage.builder().title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_DELETE)).build()).httpStatus(HttpStatus.OK).hasError(false)
                .payload(productResponse).build();
    }

    private List<ProductResponse> convertProductsResponse(List<Product> products) {
        return products.stream()
                .map(product -> ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName()).quantity(product.getQuantity()).price(product.getPrice())
                        .productCreatedDate(product.getProductCreatedDate().getTime()).productUpdatedDate(product.getProductUpdatedDate().getTime()).build()).collect(Collectors.toList());
    }

    private ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName()).quantity(product.getQuantity()).price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate().getTime()).productUpdatedDate(product.getProductUpdatedDate().getTime()).build();
    }
}
