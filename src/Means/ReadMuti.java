package Means;

import Ext2.Inode;
import Ext2.MyDirectory;
import Ext2.MyFile;

import java.util.Iterator;
import java.util.Set;

import static OS.FSystem.*;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/25 16:51
 * User:Lee
 */


public class ReadMuti {

    /**
     * 对多文件进行读取
     */
    public static void MultiRead() {
        // 判断该 目录下是否有可读取的文件
        int m = 0;
        int flag = 0;
        if (now_file instanceof MyDirectory) {
            MyDirectory now__real_file = (MyDirectory) now_file;
            m = now__real_file.getTree().size();
            if (m == 0) {
                System.out.println("次目录下没有可读的文件");
            } else {

                Set <Integer> dir_inodes = now__real_file.getTree()
                        .keySet();
                Iterator <Integer> iteratore = dir_inodes.iterator();
                while (iteratore.hasNext()) {

                    Object file = blocks[now__real_file.getTree().get(
                            iteratore.next())];
                    if (file instanceof MyDirectory) {
                        // 文件 为目录不进行读取
                    } else {
                        MyFile real_file = (MyFile) file;
                        Inode real_inode = inodes[real_file
                                .getInode_address()];
                        // 获取文件 并进行读取。
                        System.out.println(real_file.getName() + "\t");
                        System.out.println(real_file.getSubstance().substring(0,
                                real_file.getSubstance().lastIndexOf("\r\n")));
                        //记录文件的文件的个数。
                        flag++;
                    }

                }
                System.out.println("已经批量读取的文件数目为：" + flag);
            }

        } else {
            System.out.println("改目录下无可批量读取的文件！");
        }

    }

}
