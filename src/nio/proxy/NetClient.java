package nio.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class NetClient {

    private final UserServiceI userServiceI = new UserServiceImpl();

    public <T> T getBean(Class<T> serviceClass){
       return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, (proxy, method, args) -> {
           System.out.println(proxy.getClass() +":"+ Arrays.toString(args));
           return userServiceI.say((String) args[0]);
       });
    }

}
