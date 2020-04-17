package nio.proxy;

public class Client {

    public static void main(String[] args) {
        NetClient netClient = new NetClient();
        for (int i = 0; i < 10; i++) {
            UserServiceI userServiceI = (UserServiceI) netClient.getBean(UserServiceI.class);
            System.out.println(userServiceI.say("通过代理对象调用"));
        }
    }
}
