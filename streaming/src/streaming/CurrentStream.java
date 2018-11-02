package streaming;

import java.util.Optional;
import java.util.stream.Stream;

public class CurrentStream {

    private Optional<Stream> stream = Optional.empty();

    /**
     * Getter method.
     * @return the current stream.
     */
    public Optional<Stream> getCurrentStream() {
        return stream;
    }

    /**
     * Setter method.
     * @param s: the new stream.
     */
    public void setCurrentStream(Stream s) {
        stream = Optional.ofNullable(s);
    }

    /**
     * Singleton design pattern.
     */
    private CurrentStream() {}

    private static CurrentStream instance = new CurrentStream();

    public static CurrentStream getInstance() {
        return instance;
    }
}