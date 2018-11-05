package streaming;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;

import java.util.Optional;
import java.util.stream.Stream;

public class CurrentStream {

    private Optional<Stream<Matrix>> stream = Optional.empty();

    /**
     * Getter method.
     * @return the current stream.
     */
    public Optional<Stream<Matrix>> getCurrentStream() {
        return stream;
    }

    /**
     * Setter method.
     * @param s: the new stream.
     */
    public void setCurrentStream(Stream<Matrix> s) {
        stream = Optional.ofNullable(s);
    }

    /**
     * Check if there is one available stream.
     * @throws Exception if there is no stream currently available.
     */
    public static void checkIsPresent() throws Exception {
        if (!getInstance().getCurrentStream().isPresent()) {
            throw new UserException(MuttLabStrings.CURRENT_STREAM_IS_NOT_PRESENT.toString());
        }
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