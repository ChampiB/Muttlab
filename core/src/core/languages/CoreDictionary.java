package core.languages;

import muttlab.languages.DictionaryLoader;
import muttlab.languages.Language;

import java.util.HashMap;
import java.util.Map;

public class CoreDictionary extends DictionaryLoader {
    /**
     * Create mapping between Language and dictionary files.
     * @return the map build.
     */
    protected Map<Language, String> createMapping() {
        Map<Language, String> mappings = new HashMap<>();
        mappings.put(Language.ENGLISH, "./dictionaries/plugin-core-en.txt");
        return mappings;
    }

    /**
     * Singleton design pattern.
     */
    private CoreDictionary() {
        super();
    }

    private static CoreDictionary instance = new CoreDictionary();

    public static CoreDictionary getInstance() {
        return instance;
    }
}
