package liusheng.download.kugou;

import com.google.gson.Gson;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;

import java.io.IOException;

public class KugouSearchInfoParser implements Parser<String, Object> {
    private final Gson gson = new Gson();

    public Object parse(String url) throws IOException {
        String body = ConnectionUtils.getConnection(url).execute().body();
        KugouSearchEntity searchEntity = gson.fromJson(body, KugouSearchEntity.class);
        return searchEntity;
    }
}
