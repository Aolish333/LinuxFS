package Utlis;

import java.util.Arrays;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 22:18
 * User:Lee
 */

/**
 * 显示help命令
 */
public class Command {
    @Override
    public String toString() {
        return Arrays.toString(command);
    }

    String[] command = {"quit",
            "mkdir 创建目录",
            "cd 进入目录",
            "ls 显示所有文件或目录",
            "cp 复制文件",
            "mv 重命名",
            "echo 创建文件",
            "vim 写文件",
            "cat 读文件",
            "rm 删除文件或目录",
            "open 打开文件",
            "close 关闭文件",
            "MRead 批量读取文件"};

    public void say() {
        for (String help : command) {
            System.out.println(help);
        }
    }

}
