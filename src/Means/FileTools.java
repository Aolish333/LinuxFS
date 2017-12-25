package Means;

import java.io.*;

/**
 * FileTools 文件操作的工具类
 *
 * @author aolish333@gmail.com
 * @date 2017/12/22 19:59
 * User:Lee
 */
public class FileTools {
    /**
     * Object read( String name) 读取name文件返回object类型的数据
     *
     * @param name
     * @return
     */
    public static Object read(String name) {
        File file = new File(name);
        ObjectInputStream oin = null;
        Object obj = null;
        try {
            oin = new ObjectInputStream(new FileInputStream(name));
            obj = oin.readObject();
            // System.out.println("read"+obj);EOFException
        } catch (EOFException e1) {
        } catch (FileNotFoundException e) {

            write(name, null);
            read(name);
        } catch (IOException e) {

            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        try {
            if (null != oin) {
                oin.close();
            }
        } catch (IOException e) {

            System.out.println("ObjectInputStream关闭失败！");
        }
        return obj;

    }

    /**
     * write(String name,Object o) 向name目录文件写入所给的对象
     *
     * @param name
     * @param o
     */
    public static void write(String name, Object o) {
        File file = new File(name);
        ObjectOutputStream oout = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("创建原始数据文件失败！");
            }
        }

        try {
            oout = new ObjectOutputStream(new FileOutputStream(file));
            if (null != o) {
                oout.writeObject(o);
            }
        } catch (IOException e) {
            System.out.println("读取内容文件失败！");
        } finally {
            try {
                if (null != oout) {
                    oout.close();
                }
            } catch (IOException e) {
                System.out.println("ObjectOutputStream关闭失败！");
            }
        }
    }

    /**
     * 随机读取文件内容
     *
     * @param fileName 文件名
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            //将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            //一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            //将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
