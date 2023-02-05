package com.memrevatan.stockmanagement.productservice.exception.handler;

import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.memrevatan.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import com.memrevatan.stockmanagement.productservice.exception.exceptions.ProductNotFoundException;
import com.memrevatan.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.memrevatan.stockmanagement.productservice.response.FriendlyMessage;
import com.memrevatan.stockmanagement.productservice.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // ExceptionHandling yapabilmek için yazıyoruz. RestController'da hata olursa buraya gelip bu hataları handling ediyor. Yani tek bir yerde tüm hata yönetimi yapabiliyosun.
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST) // uygun bir request gelmediğinde BAD_REQUEST kullanılır. Uygun bir istek gelmezse product olusturulamaz(metot ismine bakın).
    @ExceptionHandler(ProductNotCreatedException.class)
    public InternalApiResponse<String> handleProductNotCreatedException(ProductNotCreatedException exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(),exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public InternalApiResponse<String> handleProductNotFoundException(ProductNotFoundException exception) {
        return  InternalApiResponse.<String>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public InternalApiResponse<String> globallyNullPointerExceptionHandler(Exception exception) {
        return InternalApiResponse.<String>builder()
                .friendlyMessage(null)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .hasError(true)
                .errorMessages(null)
                .build();
    }
}
