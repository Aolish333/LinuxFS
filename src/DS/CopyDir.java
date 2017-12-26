package DS;

import static Utlis.CommonWay.getFreeInode;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/22 19:21
 * User:Lee
 */
public class CopyDir {
    /**
     * 复制文件夹以及文件夹下的目录
     *      如果复制的目录是个空的文件夹，就相当于在要复制的目录下新建一个目录。
     *      如果复制的文件夹下还有一些其他的目录及文件。还要递归复制相应的文件及目录。
     */
    public static void CopyDir(String[] cmd){
        if (cmd.length < 3){
            System.out.println("您输入的命令有误！");
        }
        int index = getFreeInode();

        // 判断是否有可以分配的 节点
        if (index != -1) {

        }
    }
}
