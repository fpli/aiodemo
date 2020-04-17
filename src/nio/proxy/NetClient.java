package nio.proxy;

import java.lang.reflect.Proxy;

public class NetClient {

    private UserServiceI userServiceI = new UserServiceImpl();

    private static int count;

    public Object getBean(Class<?> serviceClass){
       return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, (proxy, method, args) -> {
           System.out.println("count="+ (++count));
           return userServiceI.say((String) args[0]);
        });
    }
}
