package common;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseInputReader<T> {

    public T readInput(String filePath) throws URISyntaxException, FileNotFoundException {
        var resource = this.getClass().getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("File not found");
        }

        return this.read(resource.toURI());
    }

    protected abstract T read(URI filePath) throws FileNotFoundException;
}
