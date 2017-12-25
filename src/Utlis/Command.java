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
            "mkdir",
            "rmdir",
            "cd",
            "mk",
            "rm",
            "create",
            "write",
            "read",
            "delete",
            "open",
            "mv",
            "MRead"};

    public void say() {
        for (String help : command) {
            System.out.println(help);
        }
    }

}
