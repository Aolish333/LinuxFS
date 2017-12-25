package Utlis;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/25 16:07
 * User:Lee
 */

import java.io.*;

/**
 * 对文件进行随机读写的测试
 */
public class RandomAccessFileTest {
    private FileInputStream fis;
    private FileOutputStream fos;

    private RandomAccessFile rasRead1;
    private RandomAccessFile rasRead2;
    private RandomAccessFile rasWrite1;
    private RandomAccessFile rasWrite2;

    public static void main(String[] args) {
        File file = new File("D:\\李宗盛 - 山丘.wav");
        System.out.println("文件长度: " + file.length() + " 字节");
        new RandomAccessFileTest().testFileStream(file, new File(
                "D:\\李宗盛 - 山丘 - 单线程.wav"));
        new RandomAccessFileTest().testRandomAccessFile(file, new File(
                "D:\\李宗盛 - 山丘 - 多线程.wav"));
    }

    private void testFileStream(File file, File newFile) {

        long time = System.currentTimeMillis();
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(newFile);
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length = fis.read(buf)) != -1) {
                fos.write(buf, 0, length);
                fos.flush();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("单线程复制共耗时: " + (System.currentTimeMillis() - time)
                + " 毫秒");
    }

    private void testRandomAccessFile(File file, File newFile) {
        long time = System.currentTimeMillis();
        try {
            rasRead1 = new RandomAccessFile(file, "rw");
            rasRead2 = new RandomAccessFile(file, "rw");
            rasWrite1 = new RandomAccessFile(newFile, "rw");
            rasWrite2 = new RandomAccessFile(newFile, "rw");

            final long halfSize = file.length() / 2;
            long leftSize = file.length() - halfSize;

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        int length = 0;
                        byte[] buf = new byte[1024];
                        while ((length = rasRead1.read(buf)) != -1) {
                            rasWrite1.write(buf, 0, length);
                            if (rasRead1.getFilePointer() >= halfSize) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        rasRead2.seek(halfSize);
                        rasWrite2.seek(halfSize);
                        int length = 0;
                        byte[] buf = new byte[1024];
                        while ((length = rasRead2.read(buf)) != -1) {
                            rasWrite2.write(buf, 0, length);
                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
//            try {
//                if (rasRead1 != null) {
//                    rasRead1.close();
//                }
//                if (rasRead2 != null) {
//                    rasRead2.close();
//                }
//                if (rasWrite1 != null) {
//                    rasWrite1.close();
//                }
//                if (rasWrite2 != null) {
//                    rasWrite2.close();
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }
        System.out.println("多线程复制共耗时: " + (System.currentTimeMillis() - time)
                + " 毫秒");
    }
}
