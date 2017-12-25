package Means;

import Ext2.Inode;
import Ext2.MyDirectory;
import Ext2.MyFile;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import static OS.FSystem.*;
import static Utlis.CommonWay.getFileByName;
import static Utlis.CommonWay.getFreeInode;

/**
 * @author yangbingyu
 * @date 2017/12/22 16:59
 */
public class FileInfo {
    static Scanner sc = new Scanner(System.in);

    public static void FilesUerInfo() {
        int m = 0;
        System.out.println("文件名\t用户名\t地址\t文件长度\t只读1/可写2\t打开控制");
        if (now_file instanceof MyDirectory) {
            MyDirectory now__real_file = (MyDirectory) now_file;
            m = now__real_file.getTree().size();
            if (m == 0) {
                System.out.println("没有目录项");
            } else {

                Set <Integer> dir_inodes = now__real_file.getTree()
                        .keySet();
                Iterator <Integer> iteratore = dir_inodes.iterator();
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
    public static void create(String[] cmd) {
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
                inode.setPath(name + "/");
            } else {
                inode.setPath(inodes[inode.getFather()].getPath()
                        + cmd[1] + "/");
            }
            // 可写
            inode.setRight(1);
            inode.setState("open");
            // 文件
            inode.setType(1);
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

    /**
     * 删除文件
     */
    public static void delete(String[] cmd) {
        Object o = getFileByName(cmd[1]);
        if (null != o) {
            if (o instanceof MyDirectory) {
                MyDirectory o1 = (MyDirectory) o;

                if (o1.getTree().size() == 0) {
                    int index = o1.getInode_address();
                    sb.setInode_free(index);
                    // 重置节点
                    inodes[index] = new Inode();
                    // 重置数据块
                    blocks[o1.getInode_address()] = new Object();
                    // 在目录的tree中删除数据
                    MyDirectory file = (MyDirectory) now_file;
                    file.getTree().remove(index);

                    System.out.println(o1.getName() + "目录已删除！");
                } else {
                    System.out.println(o1.getName() + "目录不为空！不可以删除");
                }
            } else if (o instanceof MyFile) {
                MyFile o1 = (MyFile) o;

                int index = o1.getInode_address();
                // 设置超级快
                sb.setInode_free(index);
                sb.setFreeuse(inodes[index].getLength());
                // 重置节点
                inodes[index] = new Inode();
                // 重置数据块
                blocks[o1.getInode_address()] = new Object();
                // 在目录的tree中删除数据
                MyDirectory file = (MyDirectory) now_file;
                file.getTree().remove(index);

                System.out.println(o1.getName() + "文件已删除！");

            } else {
                System.out.println(cmd[1] + "文件不存在！");
            }
        }
    }

    /**
     * 读文件
     */
    public static void readFile(String[] cmd) {
        Object o = getFileByName(cmd[1]);
        if (null != o) {
            if (o instanceof MyDirectory) {
                MyDirectory o1 = (MyDirectory) o;
                System.out.println(o1.getName() + "目录不能执行此命令！");
            } else if (o instanceof MyFile) {

                MyFile o1 = (MyFile) o;
                System.out.println(o1.getName() + "文件内容如下：");
                System.out.println(o1.getSubstance().substring(0,
                        o1.getSubstance().lastIndexOf("\r\n")));
            }
        }
    }

    /**
     * 写文件
     */
    public static void writeFile(String[] cmd) {
        Object o = getFileByName(cmd[1]);
        if (null != o) {
            if (o instanceof MyDirectory) {
                MyDirectory o1 = (MyDirectory) o;
                System.out.println(o1.getName() + "目录不能执行此命令！");
            } else if (o instanceof MyFile) {
                MyFile o1 = (MyFile) o;
                // System.out.println(o1.getName());
                System.out.println("1.续写;2.重写; 请选择");
                String select = sc.next();
                while (true) {

                    if ("1".equals(select)) {
                        System.out.println("请输入续写的数据，以#end结束");
                        StringBuffer content = new StringBuffer(o1
                                .getSubstance().substring(0, o1.getSubstance().lastIndexOf("\r\n")));
                        while (true) {
                            String tem = sc.next();
                            if (tem.equals("#end")) {
                                System.out.println("文件输入结束");
                                // 文件输入结束
                                break;
                            }
                            content.append(tem + "\r\n");
                        }
                        o1.setSubstance(content.toString());
                        System.out.println("续写操作成功！");
                        break;

                    } else if ("2".equals(select)) {
                        System.out.println("请输入重写的数据，以#end结束");
                        StringBuffer content = new StringBuffer();
                        while (true) {
                            String tem = sc.next();
                            if (tem.equals("#end")) {
                                System.out.println("文件输入结束");
                                break;// 文件输入结束
                            }
                            content.append(tem + "\r\n");
                        }
                        o1.setSubstance(content.toString());
                        System.out.println("重写操作成功！");
                        break;


                    } else {
                        System.out.println("输入错误，请重新输入！");
                        select = sc.next();
                    }
                }
            }
        } else {
            System.out.println("输入错误，请重新输入！");

        }
    }

    /**
     * 打开文件
     */
    public static void openFile(String[] cmd) {
        int index = getFreeInode();
        if (index != -1) {
            Inode inode = new Inode();
            MyFile my_file = new MyFile();
            inode.setRight(1);
            inode.setState("open");
            // 文件
            inode.setType(1);
            inode.setAddress(index);
            inodes[index] = inode;
            my_file.setInode_address(index);
            MyDirectory real_file = (MyDirectory) now_file;
            blocks[index] = my_file;
            real_file.getTree().put(index, index);
            System.out.println(cmd[1] + "文件已经打开!");

            Object o = getFileByName(cmd[1]);
            if (null != o) {
                if (o instanceof MyDirectory) {
                    MyDirectory o1 = (MyDirectory) o;
                    System.out.println(o1.getName() + "目录不能执行此命令！");
                } else if (o instanceof MyFile) {

                    MyFile o1 = (MyFile) o;
                    System.out.println(o1.getName() + "文件内容如下：");
                    System.out.println(o1.getSubstance().substring(0,
                            o1.getSubstance().lastIndexOf("\r\n")));
                }
            }
        }
    }

    /**
     * 关闭文件
     */
    public static void closeFile(String[] cmd) {
        int index = getFreeInode();
        MyFile my_file = new MyFile();
        StringBuffer content = new StringBuffer();
        my_file.setSubstance(content.toString());
        inodes[index].setLength(content.length());
        inodes[index].setState("close");
        System.out.println(cmd[1] + "文件已关闭！");
    }

    /**
     * 复制文件
     *
     * 思路：
     * 先判断命令是否否和规范
     * 判断要复制的文件是否是文件以及是否合法
     * 判断复制读路径是否合法。
     * 在对应的目录下创建新的文件并复制过去。
     */
    public static void copyFile(String[] cmd) {
        if (cmd.length < 3){
            System.out.println("您输入的命令有误！");
        }
        int index = getFreeInode();
        // 判断是否有可以分配的 节点
        if (index != -1) {

            Object o = getFileByName(cmd[1]);
            MyFile ol = (MyFile) o;
            MyFile my_file = new MyFile();
            // 设置复制的文件名字
            my_file.setName(cmd[2]);


            Inode inode = new Inode();
            inode.setFather(now_inode.getMe());
            inode.setUsers(name);
            inode.setMe(index);
            inode.setModifytime();
            if (inode.getFather() == -1) {
                inode.setPath(name + "/");
            } else {
                inode.setPath(inodes[inode.getFather()].getPath()
                        + cmd[2] + "/");
            }
            // 可写
            inode.setRight(1);
            inode.setState("open");
            // 文件
            inode.setType(1);
            inode.setAddress(index);
            inodes[index] = inode;
            my_file.setInode_address(index);
            MyDirectory real_file = (MyDirectory) now_file;
            blocks[index] = my_file;
            real_file.getTree().put(index, index);

            StringBuffer content = new StringBuffer();


            // 复制内容
            my_file.setSubstance(ol.getSubstance());

            inodes[index].setLength(ol.getSubstance().length());
            inodes[index].setState("close");
            System.out.println("Copy Ok.");
            sb.setAlreadyuse(content.length());
            sb.setInode_busy(index);
        } else {
            System.out.println("inode申请失败！");
        }

    }
}
