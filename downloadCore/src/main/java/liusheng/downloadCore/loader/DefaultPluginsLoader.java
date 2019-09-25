package liusheng.downloadCore.loader;

import liusheng.downloadInterface.Plugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultPluginsLoader implements PluginsLoader {


    private static DefaultPluginsLoader defaultPluginsLoader;
    private Map<ClassLoader, List<Plugin>> pluginsMap = new ConcurrentHashMap<>();

    public Map<ClassLoader, List<Plugin>> getPluginsMap() {
        return pluginsMap;
    }

    public static synchronized PluginsLoader getPluginsLoader() {
        if (Objects.isNull(defaultPluginsLoader)) {
            defaultPluginsLoader = new DefaultPluginsLoader();
        }
        return defaultPluginsLoader;
    }

    @Override
    public synchronized List<Plugin> load(ClassLoader classLoader)  {
        List<Plugin> plugins = pluginsMap.get(classLoader);
        if (Objects.isNull(plugins)) {
            ServiceLoader<Plugin> pluginsService = ServiceLoader.load(Plugin.class, classLoader);

            plugins = new LinkedList<>();

            for (Plugin plugin : pluginsService) {
                plugins.add(plugin);
            }
            pluginsMap.put(classLoader, plugins);
        }

        return plugins;
    }
}
