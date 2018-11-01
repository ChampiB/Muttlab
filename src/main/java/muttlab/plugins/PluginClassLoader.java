package muttlab.plugins;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

    /**
     * Constructor.
     */
    public PluginClassLoader()
    {
        super (new URL[0]);
    }

    /**
     * Add a new file in the list of available jar files.
     * Allowing to load the classes contains in it.
     * @param urlPath : The path to the file to add.
     * @throws MalformedURLException if the url is malformed.
     */
    public void addFile(String urlPath) throws MalformedURLException {
        addURL(new URL("file", "localhost", urlPath));
    }
}


