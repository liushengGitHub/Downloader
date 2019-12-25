package liusheng.download.manhuadui.donwload;


import liusheng.download.manhuadui.bean.Cartoon;
import liusheng.download.manhuadui.parse.CartoonParser;
import liusheng.downloadCore.executor.FailListExecutorService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;


public class CartoonDonwloader {
    private String dir;
    private CartoonParser cartoonParser;

    public CartoonDonwloader(String dir) {
        this.dir = dir;
        this.cartoonParser = new CartoonParser();
    }

    public void download(String url) {


        try {
            Cartoon cartoon = cartoonParser.parse(url);

            File d1 = new File(dir, cartoon.getName());

            if (!d1.exists()) {
                d1.mkdirs();
            }
            cartoon.getVersions().forEach(version -> {
                File d2 = new File(d1, version.getVersion());

                if (!d2.exists()) {
                    d2.mkdirs();
                }
                version.getChapters().forEach(chapter -> {
                    File d3 = new File(d2, version.getVersion());

                    if (!d3.exists()) {
                        d3.mkdirs();
                    }

                    FailListExecutorService.commonExecutorServicehelp().execute(() -> {
                        try {
                            new ChapterDownloader(d3.getAbsolutePath()).download(chapter.getChapterUrl());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                });
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }

    }
}
