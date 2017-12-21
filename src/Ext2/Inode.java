package Ext2;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 21:32
 * User:Lee
 */

/**
 * 记录文件的属性
 */
public class Inode {
    /**
     * 文件的类型、大小、占用的磁盘块数、占用的磁盘编号
     */
    int type;
    int fileSize;
    int blockNum;
    int[] blockIdentifier;
}
