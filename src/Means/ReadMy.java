package Means;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/25 16:51
 * User:Lee
 */

import java.io.Reader;
import java.util.ArrayList;

/**
 * 同时打开多个文件
 * 实现思路：
 *      将要读取的文件放在一个列表中，然后进行同时读取。
 */
public class ReadMy {
    private int readerQueueIndex = 0;
    private ArrayList<Reader> readerQueue = new ArrayList<Reader>();
    private Reader currentReader = null;
    private boolean doneAddingReaders = false;
    /**
     * 对多文件进行读取
     */
    public static void MultiRead(){

    }
}
