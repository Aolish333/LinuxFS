package Utlis;

import Ext2.MyDirectory;
import Ext2.MyFile;

import static OS.FSystem.blocks;
import static OS.FSystem.sb;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/22 17:24
 * User:Lee
 */

/**
 * 公共方法
 */
public class CommonWay {
    /**
     * getFreeInode() 得到空的inode
     *
     * @return
     */
    public static int getFreeInode() {

        return sb.getInode_free();
    }

    public static Object getFileByName(String name) {
        for (Object o : blocks) {
            if (o instanceof MyDirectory) {
                MyDirectory o1 = (MyDirectory) o;
                if (o1.getName().equals(name)) {
                    return o1;
                }
            } else if (o instanceof MyFile) {
                MyFile o1 = (MyFile) o;
                if (o1.getName().equals(name)) {
                    return o1;
                }
            }
        }
        return null;
    }

}
