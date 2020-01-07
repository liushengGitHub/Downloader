package liusheng.download.echo.parser;

import cn.hutool.core.collection.CollectionUtil;
import com.google.gson.Gson;
import liusheng.download.echo.entity.EchoEntity;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;

import java.io.IOException;

/**
 * 年: 2019  月: 12 日: 27 小时: 20 分钟: 05
 * 用户名: LiuSheng
 */

public class EchoInfoParser implements Parser<String, Object> {
    private final Gson gson = new Gson();

    public Object parse(String s) throws IOException {
        String body = ConnectionUtils.getConnection(s)
                .execute().body();
        return gson.fromJson(body, EchoEntity.class);
    }
}
