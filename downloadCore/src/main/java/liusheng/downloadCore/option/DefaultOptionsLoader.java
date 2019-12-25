package liusheng.downloadCore.option;

import cn.hutool.core.util.ClassLoaderUtil;
import liusheng.downloadCore.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultOptionsLoader implements OptionsLoader {
    @Override
    public List<Option> loader() {
        Path path = Paths.get("config/options");

        try {
            ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
            List<Option> options = Files.lines(path).map(str -> {
                try {

                    return Class.forName(str, true, classLoader).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(o -> Objects.nonNull(o) && o instanceof Option).map(o -> (Option) o).collect(Collectors.toList());

            return options;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
