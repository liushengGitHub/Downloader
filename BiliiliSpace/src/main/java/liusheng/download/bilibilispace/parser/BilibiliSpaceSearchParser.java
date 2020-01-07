package liusheng.download.bilibilispace.parser;

import com.google.gson.Gson;
import liusheng.download.bilibilispace.entity.SpaceEntity;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadInterface.Parser;

import java.io.IOException;

/**
 * 年: 2019  月: 12 日: 31 小时: 14 分钟: 14
 * 用户名: LiuSheng
 */

public class BilibiliSpaceSearchParser implements Parser<String,Object> {
    private final Gson gson = new Gson();
    public Object parse(String url) throws IOException {
        String body = ConnectionUtils
                .getConnection(url)
                .referrer("https://www.bilibili.com")
                .execute().body();

        return gson.fromJson(body, SpaceEntity.class);
    }
}
