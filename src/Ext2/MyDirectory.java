package Ext2;

import java.util.TreeMap;

/**
 * MyDirectory 目录存储结构
 *
 * @author yangbingyu
 *
 */
public class MyDirectory {
    // 记录文件对应Inode的索引

    private int inode_address = -1;

    private String name = "";

    private TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();

    public int getInode_address() {
        return inode_address;
    }

    public void setInode_address(int inode_address) {
        this.inode_address = inode_address;
    }

    /**
     * String getName() 得到目录的名字
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setName(String name) 设置目录的名字
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * TreeMap<Inode, Integer> getTree() 得到该目录下的所有内容
     *
     * @return
     */
    public TreeMap<Integer, Integer> getTree() {
        return tree;
    }

    /**
     * setTree(TreeMap<Inode, Integer> tree) 设置存放目录文件的TreeMap
     *
     * @param tree
     */
    public void setTree(TreeMap<Integer, Integer> tree) {
        this.tree = tree;
    }

    /**
     * setTree(Inode inode, int sub) 向目录文件的TreeMap 添加数据
     *
     * @param inode
     * @param sub
     */
    public void setTree(Inode inode, int sub) {
        this.tree.put(inode.getMe(), sub);
    }
}
