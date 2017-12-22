package Utlis;

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
}
