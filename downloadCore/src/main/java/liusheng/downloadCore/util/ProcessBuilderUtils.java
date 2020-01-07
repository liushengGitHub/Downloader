package liusheng.downloadCore.util;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class ProcessBuilderUtils {
    static Logger logger = Logger.getLogger(ProcessBuilderUtils.class);
    public static void executeAndDiscardOuput(String ... commands) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder
                .redirectErrorStream(true)
                .start();
        try {


            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                logger.debug(scanner.nextLine());
            }
            process.waitFor();

        }catch (Exception e) {
            throw  e;
        }finally {
            process.destroy();
        }
    }
}
