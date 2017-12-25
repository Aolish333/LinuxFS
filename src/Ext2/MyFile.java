package Ext2;

/**
 * MyFile  文件存储结构
 * String name 文件名字
 * substance 数据内容
 * @author yangbingyu
 *
 */
public class MyFile {


    private int inode_address=-1;
    private String name="";
    private String substance="";

    public int getInode_address() {
        return inode_address;
    }
    public void setInode_address(int inode_address) {
        this.inode_address = inode_address;
    }
    /**
     * getName() 得到文件名字
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * setName(String name) 设置文件
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * String getSubstance() 得到文件的内容
     * @return
     */
    public String getSubstance() {
        return substance;
    }
    /**
     * setSubstance(String substance) 设置文件的内容
     * @param substance
     */
    public void setSubstance(String substance) {
        this.substance = substance;
    }
}
