package com.phistory.mvc.language;

import java.util.stream.Stream;

/**
 * Enumeration the the different languages the site supports
 * <p>
 * Created by Gonzalo Carbajosa on 21/01/17.
 */
public enum Language {
    SPANISH("es"), ENGLISH("en");

    private String isoCode;

    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public static Language map(String isoCode) {
        return Stream.of(Language.values())
                     .filter(language -> language.getIsoCode().equals(isoCode))
                     .findFirst()
                     .orElse(null);
    }
}
