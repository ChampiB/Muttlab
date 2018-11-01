package muttlab.config;

import muttlab.languages.Language;

public class DefaultConfig {
    /**
     * Return the default language configuration.
     * @return the default language.
     */
    public static Language getDefaultLanguage() {
        return Language.ENGLISH;
    }
}
