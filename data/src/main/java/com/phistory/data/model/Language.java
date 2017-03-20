package com.phistory.data.model;

/**
 * Enumeration of languages
 *
 * @author gonzalo
 */
public enum Language {

    SPANISH("spanish"),
    ENGLISH("english"),
    ITALIAN("italian"),
    FRENCH("french"),
    GERMAN("german"),
    JAPANESE("japanese"),
    CHINESE("chinese"),
    OTHER("other");

    private String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the {@link Language} identified by the supplied name
     *
     * @param name
     * @return The {@link Language}
     */
    public Language getByName(String name) {
        return Language.valueOf(Language.class, name);
    }
}
