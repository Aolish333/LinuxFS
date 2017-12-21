import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author aolish333@gmail.com
 * @date 2017/12/20 21:29
 * User:Lee
 */
public class Main {
    public static void main(String[] args) {
        String IP = null;
        String host = null;
        try
        {
            InetAddress ia = InetAddress.getLocalHost();
            //获取计算机主机名
            host = ia.getHostName();
            //获取计算机IP
            IP= ia.getHostAddress();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        System.out.println(host);
        System.out.println(IP);
    }
}
