package hello;
import liusheng.downloadCore.util.ProcessBuilderUtils;
import org.junit.Test;

/**
 * 年: 2020  月: 01 日: 08 小时: 14 分钟: 35
 * 用户名: LiuSheng
 */

public class Main {
    public static void main(String[] args) throws Exception {
        ProcessBuilderUtils
                .executeAndDiscardOuput("ffmpeg.exe" ,"-y"  ,"-i","\"video/零基础英语首发班（已完结）/2_02零基础礼包之公式基本概念【瑞客论坛www.ruike1.com】.flv.temp\"", "-i","\"video/零基础英语首发班（已完结）/2_02零基础礼包之公式基本概念【瑞客论坛www.ruike1.com】.mp3.temp\""
                        ,"-c", "copy" ,"\"2.mp4\"");
    }
    @Test
    public  void test1() throws Exception {
        ProcessBuilderUtils
                .executeAndDiscardOuput("explorer", "/e,/select," , "\"G:\\Project\\download\\download - 副本\\video\\尚硅谷Kafka教程(kafka框架快速入门)\"");
    }
}
