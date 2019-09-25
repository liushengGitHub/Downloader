package liusheng.downloadCore.loader;

import cn.hutool.core.util.ClassLoaderUtil;
import liusheng.downloadInterface.Plugin;

import java.io.IOException;
import java.util.List;

public interface PluginsLoader {
    List<Plugin> load(ClassLoader classLoader) ;

    default List<Plugin> load() throws IOException {
        return load(ClassLoaderUtil.getClassLoader());
    }
}
