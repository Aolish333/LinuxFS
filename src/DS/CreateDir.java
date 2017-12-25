package DS;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 22:29
 * User:Lee
 */

import Ext2.Inode;
import Ext2.MyDirectory;

import static OS.FSystem.*;
import static Utlis.CommonWay.getFreeInode;

/**
 * 创建目录
 */
public class CreateDir {
    public static void mkdir(String[] cmd){
        int index = getFreeInode();
        if (index != -1) {
            MyDirectory my_file = new MyDirectory();
            my_file.setName(cmd[1]);
            Inode inode = new Inode();
            inode.setFather(now_inode.getMe());
            inode.setUsers(name);
            inode.setMe(index);
            inode.setModifytime();
            inode.setPath(now_inode.getPath() + cmd[1] + "/");
            // 可写
            inode.setRight(1);
            // 文件
            inode.setType(0);
            inode.setAddress(index);
            inodes[index] = inode;
            my_file.setInode_address(index);

            MyDirectory real_file = (MyDirectory) now_file;
            blocks[index] = my_file;
            real_file.getTree().put(index, index);
            inodes[index].setLength(0);
            sb.setInode_busy(index);

        } else {
            System.out.println("inode申请失败！");
        }
    }
}
