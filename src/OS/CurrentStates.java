package OS;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/21 16:40
 * User:Lee
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

import static OS.FSystem.now_inode;

/**
 * 显示当前目录状态
 */
public class CurrentStates {

    /**
     * 用户包裹前缀
     */
    public static void showBefore(String name){

        String host = CurrentStates.UserShow();
        System.out.print("["+host+"@"+name);
    }

    /**
     * 用户包裹后缀
     */

    public static void showAfter(){
        System.out.print(now_inode.getPath()+ " ~]# ");
    }

    /**
     * 获得主机名
     * @return 主机名
     */
    public static String UserShow() {
        String host = null;
        try {
            InetAddress ia = InetAddress.getLocalHost();
            //获取计算机主机名
            host = ia.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
