package streaming.languages;

import muttlab.languages.DictionaryLoader;
import muttlab.languages.Language;

import java.util.HashMap;
import java.util.Map;

public class StreamingDictionary extends DictionaryLoader {
    /**
     * Create mapping between Language and dictionary files.
     * @return the map build.
     */
    protected Map<Language, String> createMapping() {
        Map<Language, String> mappings = new HashMap<>();
        mappings.put(Language.ENGLISH, "./dictionaries/plugin-streaming-en.txt");
        return mappings;
    }

    /**
     * Singleton design pattern.
     */
    private StreamingDictionary() {
        super();
    }

    private static StreamingDictionary instance = new StreamingDictionary();

    public static StreamingDictionary getInstance() {
        return instance;
    }
}
