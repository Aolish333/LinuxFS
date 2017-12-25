package OS;

import DS.CreateDir;
import DS.EnterDir;
import DS.RenameDir;
import Ext2.Inode;
import Ext2.MyDirectory;
import Ext2.SuperBlock;
import Means.FileInfo;
import Means.FileTools;
import Means.ReadMuti;
import Utlis.Command;

import java.util.ArrayList;
import java.util.Scanner;

import static Utlis.CommonWay.getFreeInode;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/21 15:43
 * User:Lee
 */
public class FSystem {
    /**
     * SuperBlock 超级块 记录虚拟磁盘的总信息
     * 用户名数组;
     * i节点记录数据结构
     * 文件块的结构；
     * 当前登录用户名
     * 当前节点
     */

    public static SuperBlock sb = null;
    public static ArrayList <String> users;
    public static Inode[] inodes = new Inode[100];
    public static Object[] blocks = new Object[100];
    public static String name = null;
    public static Inode now_inode = null;
    public static Object now_file = null;

    static Scanner sc = new Scanner(System.in);


    /**
     * 初始化文件
     */
    public void init() {
        users = (ArrayList <String>) FileTools.read("users.txt");


        sb = (SuperBlock) FileTools.read("super.txt");
        if (null == sb || sb.getAlreadyuse() == 0) {
            for (int i = 0; i < 100; i++) {
                inodes[i] = new Inode();
            }
            sb = new SuperBlock();
            for (int i = 0; i < 100; i++) {
                sb.setInode_free(i);
            }
            FileTools.write("super.txt", sb);
        }
        if (null == users) {
            // 存放整个文件系统
            users = new ArrayList <String>();
            users.add("admin");
            FileTools.write("users.txt", users);
        }
        System.out.println("欢迎来到OS系统，请先登录。");

    }

    /**
     * 登陆
     */
    public void login() {

        System.out.print("username:");
        name = sc.next();
        if (!this.isInNames(name)) {
            System.out.println("该用户名不存在！是否注册该用户？y/n");
            if ("y".equals(sc.next())) {
                    if (regeist(name)) {
                    System.out.println(name + "注册成功！");
                    login();
                } else {
                    System.out.println("注册失败！");
                    System.exit(0);
                }

            } else {
                login();
            }

        } else {
            // 得到当前的inode
            now_inode = getInode(name + "/");
            // 得到当前的目录
            now_file = blocks[now_inode.getAddress()];
            System.out.println("登录成功");
            meun();
        }
    }

    /**
     * 文件系统的主界面
     */
    public static void meun() {


        String commond = null;
        String cmd[] = null;

        while (true) {
            // 显示当前状态
            CurrentStates.showBefore(name);
            CurrentStates.showAfter();

            commond = sc.nextLine();
            if (commond.equals("")) {
                commond = sc.nextLine();
            }
            cmd = commond.trim().split(" ");

            switch (cmd[0]) {
                case "help":
                    //输出 help 命令
                    Command command = new Command();
                    command.say();
                    break;
                case "dir" :
                    FileInfo.FilesUerInfo();
                    break;
                case "create" :
                    FileInfo.create(cmd);
                    break;
                case "mkdir" :
                    CreateDir.mkdir(cmd);
                    break;
                case "delete":
                    FileInfo.delete(cmd);
                    break;
                case "cd" :
                    EnterDir.cd(cmd);
                    break;
                case "read":
                    FileInfo.readFile(cmd);
                    break;
                case "write":
                    FileInfo.writeFile(cmd);
                    break;
                case "open":
                    FileInfo.openFile(cmd);
                    break;
                case "close":
                    FileInfo.closeFile(cmd);
                    break;
                case "exit" :
                    System.exit(0);
                    break;
                case "mv" :
                    RenameDir.rename(cmd);
                    break;
                case "MRead" :
                    ReadMuti.MultiRead();
                    break;
                case "cp" :
                    FileInfo.copyFile(cmd);
                    break;
                default:
                    for (String st : cmd) {
                        System.out.println(st);
                    }
                    System.out.println("您所输入的命令有误，请检查！");
                    System.out.println("或者你可以输入 help 进行帮助");
            }
        }//while
    }


    /**
     * regeist(String name) 注册用户
     *
     * @param name
     */
    public boolean regeist(String name) {
        int inode_free_index = getFreeInode();
        if (inode_free_index > -1) {
            now_inode = inodes[inode_free_index];
            // 文件快的地址
            now_inode.setAddress(inode_free_index);
            now_inode.setModifytime();
            now_inode.setRight(1);
            now_inode.setState("close");
            now_inode.setType(0);
            now_inode.setUsers(name);
            now_inode.setPath(name + "/");
            // 当前Inode的索引
            now_inode.setMe(inode_free_index);
            inodes[inode_free_index] = now_inode;
            MyDirectory block = new MyDirectory();
            block.setName(name);
            blocks[inode_free_index] = block;
            users.add(name);
            FileTools.write("users.txt", users);
            FileTools.write("inodes.txt", inodes);
            return true;
        }

        return false;
    }


    /**
     * isInNames(String name) 判断用户名是否存在
     *
     * @param name
     * @return
     */
    private boolean isInNames(String name) {
        for (String n : users) {
            if (n.equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * getInode(String path) 由path得到Inode
     *
     * @return
     */
    private Inode getInode(String path) {
        for (int i = 0; i < 100; i++) {
            if (path.equals(inodes[i].getPath())) {
                return inodes[i];
            }
        }
        return null;
    }

    /**
     * getBlock() 得到空闲的block的序号
     *
     * @return
     */
    private int getBlock() {
        for (int i = 0; i < 100; i++) {
            if (null == blocks[i]) {
                return i;
            }
        }
        return -1;
    }
}
