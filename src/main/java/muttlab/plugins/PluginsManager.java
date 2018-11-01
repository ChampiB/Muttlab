package muttlab.plugins;

import muttlab.languages.Language;
import muttlab.languages.LanguageObservable;
import muttlab.loggers.LoggingLevel;
import muttlab.loggers.Logging;

import java.io.File;
import java.net.URL;
import java.util.*;

public class PluginsManager implements Observer {

    private List<Plugin> plugins;

    /**
     * Singleton design pattern.
     */
    private static PluginsManager instance = new PluginsManager();
    public static PluginsManager get() {
        return instance;
    }
    private PluginsManager() {
        plugins = new ArrayList<>();
        try {
            List<String> jarsName = getListOfJarFiles("./plugins");
            for (String jarName : jarsName) {
                Object plugin = loadPluginFrom(jarName);
                if (plugin instanceof Plugin) {
                    plugins.add((Plugin) plugin);
                }
            }
        } catch (Exception e) {}
    }

    /**
     * Load plugin from jar file.
     * @param jarName : The jar file to load.
     * @return A plugin instance.
     * @throws Exception if the loading fail.
     */
    public Object loadPluginFrom(String jarName) throws Exception{
        String pluginName = jarName.substring(0, jarName.length() - 4);
        PluginClassLoader cl = new PluginClassLoader();
        cl.addFile("./plugins/" + pluginName + ".jar");
        Class pluginClass = cl.loadClass(pluginName + ".Plugin");
        return pluginClass.newInstance();
    }

    /**
     * Read the directory content.
     * @param directory : The directory to read.
     * @return the list of jar files contains in the directory.
     */
    public List<String> getListOfJarFiles(String directory) {
        final File folder = new File(directory);
        List<String> jarsName = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile() && fileEntry.getName().matches("^.*\\.jar$")) {
                jarsName.add(fileEntry.getName());
            }
        }
        return jarsName;
    }

    /**
     * Change the language in every plugin.
     * @param observable : The observable that notify us.
     * @param o : The object that have been updated.
     */
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof LanguageObservable && o instanceof Language) {
            Language language = (Language) o;
            for (Plugin plugin: plugins) {
                if (!plugin.setLanguage(language)) {
                    String message = "Plugin " + plugin.getName() + " does not support language: " + language.toString() + ".";
                    Logging.log(LoggingLevel.WARNING, message);
                }
            }
        }
    }

    /**
     * Getter method.
     * @return the list of available plugins.
     */
    public Iterator<Plugin> getAvailablePlugins() {
        return plugins.iterator();
    }
}
