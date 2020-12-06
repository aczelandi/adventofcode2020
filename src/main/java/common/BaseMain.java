package common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

public class BaseMain {

    public <T> T readInput(String filePath, Function<URI, T> reader) throws URISyntaxException {
        var resource = this.getClass().getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        }

        return reader.apply(resource.toURI());
    }
}
