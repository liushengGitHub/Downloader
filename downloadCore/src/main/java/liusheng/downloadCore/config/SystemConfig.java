package liusheng.downloadCore.config;

/**
 * 年: 2019  月: 12 日: 25 小时: 11 分钟: 13
 * 用户名: LiuSheng
 */

public class SystemConfig {
    private SystemConfig() {
    }

    private static int taskNumber = 5;

    public static int taskNumber() {
        return taskNumber;
    }

    public static int getTaskNumber() {
        return taskNumber;
    }

    public static void setTaskNumber(int taskNumber) {
        SystemConfig.taskNumber = taskNumber;
    }
}
