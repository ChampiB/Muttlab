package muttlab.languages;

import java.util.Observable;

/**
 * Singleton that notify all the observers when the language is modify.
 */
public class LanguageObservable extends Observable {

    /**
     * Singleton design pattern.
     */
    private static LanguageObservable instance = new LanguageObservable();

    public static LanguageObservable get() {
        return instance;
    }

    private LanguageObservable() {}

    /**
     * Function used to change the language.
     * @param language : The new language to use.
     */
    public void changeLanguage(Language language) {
        setChanged();
        notifyObservers(language);
    }
}
