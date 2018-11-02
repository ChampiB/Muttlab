package muttlab.config;

import muttlab.languages.Language;

public class DefaultConfig {
    /**
     * Return the application name.
     * @return the application name.
     */
    public static String getApplicationName() {
        return "MuttLab";
    }

    /**
     * Return the default language configuration.
     * @return the default language.
     */
    public static Language getDefaultLanguage() {
        return Language.ENGLISH;
    }

    /**
     * Return the plugins directory.
     * @return the plugins directory.
     */
    public static String getPluginsDirectory() {
        return "./bin/plugins/";
    }
}
