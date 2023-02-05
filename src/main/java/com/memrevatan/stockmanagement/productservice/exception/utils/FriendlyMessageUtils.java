package com.memrevatan.stockmanagement.productservice.exception.utils;

import com.memrevatan.stockmanagement.productservice.enums.Language;
import com.memrevatan.stockmanagement.productservice.exception.enums.IFriendlyMessageCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j // log'ları daha okunabilir yapan lombok annotations
@UtilityClass // class arka tarafta final olarak işaretlenir. tüm metotlar field'lar inner classlar static olarak işaretleniyor.
public class FriendlyMessageUtils {
    private static final String RESOURCE_BUNDLE_NAME = "FriendlyMessage";
    private static final String SPECIAL_CHARACTER = "__";

    public static String getFriendlyMessage(Language language, IFriendlyMessageCode messageCode) {
        String messageKey = null;
        try {
            Locale locale = new Locale(language.name());
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
            String messageGetSimpleName = messageCode.getClass().getSimpleName();
            messageKey = messageGetSimpleName + SPECIAL_CHARACTER + messageCode;
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException missingResourceException) {
            log.error("Friendly message not found for key : {}", messageKey);
            return null;
        }
    }

}
