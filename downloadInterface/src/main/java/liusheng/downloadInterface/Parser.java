package liusheng.downloadInterface;

import java.io.IOException;

public interface Parser<I,O> {
    O parse(I i) throws IOException;
}
