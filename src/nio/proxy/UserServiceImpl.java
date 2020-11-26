package nio.proxy;

public class UserServiceImpl implements UserServiceI{

    @Override
    public String say(String str) {
        return " proxy " + str;
    }

}
