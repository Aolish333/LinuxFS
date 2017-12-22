package OS;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/21 16:40
 * User:Lee
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 显示当前目录状态
 */
public class CurrentStates {

    /**
     *
     */
    public static void show(){

        String host = CurrentStates.UserShow();
        System.out.print("["+host+"@root ~]# ");
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
