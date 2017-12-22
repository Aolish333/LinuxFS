package Means;

import Ext2.Inode;
import Ext2.MyDirectory;
import Ext2.MyFile;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import static OS.FSystem.*;
import static Utlis.CommonWay.getFreeInode;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/22 16:59
 * User:Lee
 */
public class FileInfo {
    static Scanner sc = new Scanner(System.in);

    public static void FilesUerInfo(){
        int m = 0;
        System.out.println("文件名\t用户名\t地址\t文件长度\t只读1/可写2\t打开控制");
        if (now_file instanceof MyDirectory) {
            MyDirectory now__real_file = (MyDirectory) now_file;
            m = now__real_file.getTree().size();
            if (m == 0) {
                System.out.println("没有目录项");
            } else {

                Set<Integer> dir_inodes = now__real_file.getTree()
                        .keySet();
                Iterator<Integer> iteratore = dir_inodes.iterator();
                while (iteratore.hasNext()) {

                    Object file = blocks[now__real_file.getTree().get(
                            iteratore.next())];
                    if (file instanceof MyDirectory) {
                        MyDirectory real_file = (MyDirectory) file;
                        Inode real_inode = inodes[real_file
                                .getInode_address()];
                        // "文件名\t用户名\t地址\t文件长度\t只读1/可写2\t打开控制\t创建时间"
                        System.out.println(real_file.getName() + "\t"
                                + real_inode.getUsers() + "\t"
                                + real_inode.getAddress() + "\t"
                                + real_inode.getLength() + "B\t"
                                + real_inode.getRight() + "\t"
                                + real_inode.getState() + "\t"
                                + real_inode.getModifytime());

                    } else {
                        MyFile real_file = (MyFile) file;
                        Inode real_inode = inodes[real_file
                                .getInode_address()];
                        System.out.println(real_file.getName() + "\t"
                                + real_inode.getUsers() + "\t"
                                + real_inode.getAddress() + "\t"
                                + real_inode.getLength() + "B\t"
                                + real_inode.getRight() + "\t"
                                + real_inode.getState() + "\t"
                                + real_inode.getModifytime());

                    }

                }
                System.out.println("文件个数---" + m);
            }

        } else {
            MyFile now__real_file = (MyFile) now_file;
        }
    }

    /**
     * 创建文件
     */
    public static void create(String[] cmd){
        int index = getFreeInode();
        if (index != -1) {
            MyFile my_file = new MyFile();
            my_file.setName(cmd[1]);
            Inode inode = new Inode();
            inode.setFather(now_inode.getMe());
            inode.setUsers(name);
            inode.setMe(index);
            inode.setModifytime();
            if (inode.getFather() == -1) {
                inode.setPath(name + "->");
            } else {
                inode.setPath(inodes[inode.getFather()].getPath()
                        + cmd[1] + "->");
            }
            inode.setRight(1);// 可写
            inode.setState("open");
            inode.setType(1);// 文件
            inode.setAddress(index);
            inodes[index] = inode;
            my_file.setInode_address(index);
            MyDirectory real_file = (MyDirectory) now_file;
            blocks[index] = my_file;
            real_file.getTree().put(index, index);
            System.out.println(cmd[1] + "文件已经打开！请输入内容。。。#end结束输入");
            StringBuffer content = new StringBuffer();
            while (true) {
                String tem = sc.nextLine();
                if (tem.equals("#end")) {
                    System.out.println("文件输入结束");
                    break;// 文件输入结束
                }
                content.append(tem + "\r\n");
            }
            my_file.setSubstance(content.toString());
            inodes[index].setLength(content.length());
            inodes[index].setState("close");
            System.out.println(cmd[1] + "文件已关闭！");
            sb.setAlreadyuse(content.length());
            sb.setInode_busy(index);
        } else {
            System.out.println("inode申请失败！");
        }
    }

}
