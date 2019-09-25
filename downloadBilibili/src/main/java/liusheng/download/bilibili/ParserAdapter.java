package liusheng.download.bilibili;

import liusheng.download.bilibili.entity.AdapterParam;
import liusheng.downloadInterface.Parser;

import java.io.IOException;

public interface ParserAdapter {

    Parser<String,?> handle(AdapterParam adapterParam) throws IOException;
}
