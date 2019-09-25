package liusheng.downloadCore.util;

import liusheng.downloadCore.loader.DefaultPluginsLoader;
import liusheng.downloadInterface.PagePlugin;
import liusheng.downloadInterface.Plugin;
import liusheng.downloadInterface.SearchPlugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PluginUtils {
    private static List<SearchPlugin> searchPlugins = new LinkedList<>();
    private static List<PagePlugin> pagePlugins = new LinkedList<>();
    private static List<Plugin> plugins = new LinkedList<>();

    static {
        try {
            List<URLClassLoader> classLoaders = Files.list(Paths.get("plugins")).map(path -> {
                try {
                    return URLClassLoader.newInstance(new URL[]{path.toUri().toURL()});
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

            plugins = classLoaders.stream().map(DefaultPluginsLoader.getPluginsLoader()::load).flatMap(List::stream).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        plugins = Collections.unmodifiableList(plugins);

    }

    public synchronized static List<SearchPlugin> getSearchPlugins() {
        if (!searchPlugins.isEmpty()) return searchPlugins;
        try {

            searchPlugins = plugins.stream().filter(o -> o instanceof SearchPlugin)
                    .map(o -> (SearchPlugin) o).collect(Collectors.toList());
            return searchPlugins;
        } catch (Exception e) {
            return searchPlugins = Collections.emptyList();
        }
    }

    public synchronized static List<PagePlugin> getPagePlugins() {
        if (!pagePlugins.isEmpty()) return pagePlugins;
        try {

            pagePlugins = plugins.stream().filter(o -> o instanceof PagePlugin)
                    .map(o -> (PagePlugin) o).collect(Collectors.toList());
            return pagePlugins;
        } catch (Exception e) {
            return pagePlugins = Collections.emptyList();
        }
    }
}
