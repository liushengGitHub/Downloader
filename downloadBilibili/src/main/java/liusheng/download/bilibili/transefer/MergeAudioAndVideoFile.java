package liusheng.download.bilibili.transefer;

import liusheng.downloadCore.util.ProcessBuilderUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MergeAudioAndVideoFile implements Runnable {
    private Path flvPath;
    private Path mp3Path;
    private String name;
    static Logger logger = Logger.getLogger(MergeAudioAndVideoFile.class);

    public MergeAudioAndVideoFile(Path flvPath, Path mp3Path, String name) {
        this.name = name;
        this.flvPath = flvPath;
        this.mp3Path = mp3Path;
    }

    @Override
    public void run() {


        try {
            ////ffmpeg -i /path/to/input-no-audio.mp4 -i input.mp3 -c copy /path/to/output.mp4
            //ffmpeg -f concat -i mylist.txt -c copy output.flv

            String string = flvPath.toString();
            int i = string.lastIndexOf(File.separator);
            if (i != -1) {
                string = string.substring(0, i);
            }

            ProcessBuilderUtils.executeAndDiscardOuput("ffmpeg", "-y", "-i", flvPath.toString(), "-i", mp3Path.toString(), "-c", "copy", string + File.separator + name + ".mp4");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 在 Download 后删除
            /*try {
                Files.delete(flvPath);
                Files.delete(mp3Path);
            } catch (IOException e) {
                logger.debug("删除文件失败");
            }*/
        }
    }
}
