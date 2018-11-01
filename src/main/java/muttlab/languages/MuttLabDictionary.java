package muttlab.languages;

import java.util.HashMap;
import java.util.Map;

public class MuttLabDictionary extends DictionaryLoader {
    /**
     * Create mapping between Language and dictionary files.
     * @return the map build.
     */
    protected Map<Language, String> createMapping() {
        Map<Language, String> mappings = new HashMap<>();
        mappings.put(Language.ENGLISH, "./dictionaries/muttlab-en.txt");
        return mappings;
    }

    /**
     * Singleton design pattern.
     */
    private MuttLabDictionary() {
        super();
    }

    private static MuttLabDictionary instance = new MuttLabDictionary();

    public static MuttLabDictionary getInstance() {
        return instance;
    }
}
