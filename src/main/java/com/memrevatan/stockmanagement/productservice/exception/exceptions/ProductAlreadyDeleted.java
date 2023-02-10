package com.memrevatan.stockmanagement.productservice.exception.exceptions;

import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.IFriendlyMessageCode;
import com.memrevatan.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ProductAlreadyDeleted extends RuntimeException{
    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public ProductAlreadyDeleted(Language language, IFriendlyMessageCode friendlyMessageCode, String developerMessage) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ProductNotFoundException] -> message:{} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode), developerMessage);
    }
}
