package DS;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 22:27
 * User:Lee
 */

import Ext2.MyDirectory;

import static OS.FSystem.*;
import static Utlis.CommonWay.getFileByName;

/**
 * 进入目录
 */
public class EnterDir {

    public static void cd(String[] cmd){
        if (".".equals(cmd[1])) {

        } else if ("..".equals(cmd[1])) {
            if (now_inode.getFather() == -1) {
                System.out.println("当前目录为根目录！");
            } else {
                MyDirectory now_directory = (MyDirectory) now_file;
                now_inode = inodes[now_inode.getFather()];
                now_file = blocks[now_inode.getAddress()];
            }
        } else if (null != getFileByName(cmd[1])) {
            Object o1 = getFileByName(cmd[1]);
            if (o1 instanceof MyDirectory) {
                MyDirectory o = (MyDirectory) o1;
                now_file = o;
                now_inode = inodes[o.getInode_address()];
            } else {
                System.out.println("输入的目录不存在，请检查！");
            }

        } else {
            System.out.println("输入的目录不存在，请检查！");
        }
    }

}
