package muttlab.languages;

import muttlab.config.DefaultConfig;
import muttlab.loggers.LoggingLevel;
import muttlab.loggers.Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public abstract class DictionaryLoader implements Observer {

    private Map<String, String> currentDictionary = new HashMap<>();
    private Map<Language, String> dictionariesMapping = createMapping();

    /**
     * Create mapping between Language and dictionary files.
     * @return the map build.
     */
    protected abstract Map<Language, String> createMapping();

    /**
     * Default constructor (initialize the current dictionary to the default language).
     */
    public DictionaryLoader() {
        setLanguage(DefaultConfig.getDefaultLanguage());
    }

    /**
     * Load pair of key-value from the string passed as parameter.
     * @param line : The line that contains the key-value.
     */
    private void loadKeyValue(String line) {
        String[] keyValue = line.split("=", 2);
        currentDictionary.put(keyValue[0], keyValue[1].replaceAll("\\\\n", "\n"));
    }

    /**
     * Change the language of the dictionary.
     * @param language: The language.
     */
    public void setLanguage(Language language) {
        try {
            String fileName = dictionariesMapping.get(language);
            currentDictionary.clear();
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line;
            while ((line = br.readLine()) != null) {
                loadKeyValue(line);
            }
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
        }
    }

    /**
     * Get the value from the key.
     * @param key : The key.
     * @return : The value.
     */
    public String getValue(String key) {
        String value = currentDictionary.get(key);
        if (value == null) {
            Logging.log(LoggingLevel.WARNING, "No key '" + key + "' in the current dictionary.");
            value = "";
        }
        return value;
    }

    /**
     * Get the key from the value.
     * @param value : The value.
     * @return the key.
     */
    public String getKey(String value) {
        for (Map.Entry<String, String> entry : currentDictionary.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Observer design pattern.
     */
    private void updateLanguage(Language language) {
        setLanguage(language);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof LanguageObservable && o instanceof Language) {
            updateLanguage((Language)o);
        }
    }
}
