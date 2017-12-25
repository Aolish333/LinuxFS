package Ext2;

import org.junit.Test;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 21:33
 * User:Lee
 */

/**
 * String user; 用户 int length; 文件的长度 int right; 文件的读写权限//0只读/1只写 String
 * state;文件是否打开的标志 String modifytime; 文件的修改时间 address;//对应文件块的地址---即序号
 * <p>
 * String path 路径；
 *
 */
public class Inode implements Serializable, Comparable <Inode> {

    private String path = "";
    private int length = 0;
    private String users = "";
    private int right = 1;
    private String state = "close";
    private String modifytime;
    private int type = 0;
    private int address = -1;
    private int father = -1;
    private int me = -1;


    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getMe() {
        return me;
    }

    public void setMe(int me) {
        this.me = me;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRight() {
        if (this.right == 0) {
            return "R";
        } else {
            return "W";
        }
    }

    public void setRight(int right) {
        this.right = right;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModifytime() {
        return modifytime;
    }

    /**
     * setModifytime() 设定文件的最后修改日期
     */
    @Test
    public void setModifytime() {
        Date date = new Date();
        SimpleDateFormat adf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        // System.out.println(adf.format(date));
        this.modifytime = adf.format(date);
    }

    @Override
    public String toString() {
        return this.getUsers() + "\t" + this.getLength() + "b\t"
                + this.getRight() + "\t" + this.getModifytime();
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    /**
     * Comparable 的方法，为了实现INode的有序；
     */
    @Override
    public int compareTo(Inode o) {

        return (this.modifytime.hashCode() + this.getType()) - (o.modifytime.hashCode() + o.getType());
    }

}
