package liusheng.download.manhuadui.donwload;


import liusheng.download.manhuadui.bean.Image;
import liusheng.download.manhuadui.parse.ChapterImageParser;
import liusheng.downloadCore.util.ConnectionUtils;
import liusheng.downloadCore.util.FileUtils;
import liusheng.downloadCore.util.StringUtils;
import liusheng.downloadInterface.Parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class ChapterDownloader {
    private final String dir;
    private Parser<String, List<Image>> parser = new ChapterImageParser();

    public ChapterDownloader(String dir) {

        this.dir = dir;
    }

    public void download(String url) {

        String name = StringUtils.urlToFileName(url);
        int index = name.lastIndexOf(".");
        if (index != -1) name = name.substring(0, index);

        File dirFile = new File(dir, name);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            List<Image> images = parser.parse(url);

            System.out.println(url);

            IntStream.rangeClosed(1, images.size()).parallel().forEach(i -> {
                String refer = url + "?p=" + i;
                try {
                    Image image = images.get(i - 1);
                    BufferedInputStream inputStream = ConnectionUtils.getConnection(image.getUrl()).header("Referer", refer).timeout(120000).maxBodySize(100 * 1024 * 1024)
                            .execute().bodyStream();


                    FileUtils.copy(inputStream, new FileOutputStream(new File(dirFile, image.getFileName())));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
