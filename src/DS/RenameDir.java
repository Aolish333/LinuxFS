package DS;

import Ext2.MyDirectory;
import Ext2.MyFile;

import static OS.FSystem.inodes;
import static OS.FSystem.now_inode;
import static Utlis.CommonWay.getFileByName;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/25 14:39
 * User:Lee
 */
public class RenameDir {

    /**
     * rename(String[] cmd) 重命名函数
     *
     * @param cmd
     * @return
     */
    public static boolean rename(String[] cmd) {

        if (cmd.length < 3) {
            System.out.println("输入格式错误！eg. mv A B");
            return false;
        }
        Object o = getFileByName(cmd[1]);
        if (null == o) {
            return false;
        } else {
            if (o instanceof MyDirectory) {
                MyDirectory oo = (MyDirectory) o;
                oo.setName(cmd[2]);
                inodes[oo.getInode_address()].setPath(now_inode.getPath()
                        + cmd[2] + "/");
                return true;
            } else {
                MyFile oo = (MyFile) o;
                oo.setName(cmd[2]);
                return true;
            }
        }

    }
}
