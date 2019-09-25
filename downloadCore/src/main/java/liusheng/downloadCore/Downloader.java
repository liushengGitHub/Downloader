package liusheng.downloadCore;

import java.io.IOException;

public interface Downloader<T> {
    Error download(T t) throws IOException;
}
