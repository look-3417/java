package test1;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test1 {

    static{
        System.out.println("你启动了Test1类的main方法，mysql驱动被注册了");
        //com.mysql.cj.jdbc.Driver 是mysql提供的实现了java.sql.Driver接口的驱动类
        try {
            //注册mysql驱动,只需要做一次
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载mysql驱动
     * @param args
     */
    public static void main(String[] args) {

    }
}
