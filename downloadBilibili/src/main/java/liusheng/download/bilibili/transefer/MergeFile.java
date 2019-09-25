package liusheng.download.bilibili.transefer;

import liusheng.downloadCore.util.ProcessBuilderUtils;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MergeFile implements Runnable {
    private final List<String> list;
    private final String name;
    private final String dir;
    private final Semaphore semaphore;
    private final Logger logger = Logger.getLogger(MergeFile.class);

    public MergeFile(List<String> list, String name, String dir, Semaphore semaphore) {
        this.list = list;
        this.name = name;
        this.dir = dir;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            writeAccord();
            //ffmpeg -f concat -i mylist.txt -c copy output.flv
            // 有点批问题
            //./ffmpeg.exe -f concat -safe 0 -i ./fileToMerge.txt -c copy -y ./out.mp4
            ProcessBuilderUtils.executeAndDiscardOuput("ffmpeg", "-y", "-f", "concat", "-safe", "0", "-i", dir + File.separator + name + ".txt", "-c", "copy", dir + File.separator + name + ".mp4");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                list.forEach(f -> {
                    try {
                        Files.delete(Paths.get(f));
                    } catch (IOException e) {

                    }
                });
                Files.delete(Paths.get(dir, name + ".txt"));
            } catch (IOException e) {
                logger.debug("删除失败");
            }
        }

    }

    private void writeAccord() throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(dir, name + ".txt"));
        try {
            list.forEach(n -> {
                try {
                    // 在ffmpeg 中 需要存在两个\\才行
                    bufferedWriter.write("file  " + n.replace("\\", "\\\\"));
                    bufferedWriter.newLine();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            bufferedWriter.close();
        }
    }
}
