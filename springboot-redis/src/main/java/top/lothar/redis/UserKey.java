package top.lothar.redis;

public class UserKey extends BaseKeyPrefix{


    public UserKey(String prefix) {
        super(prefix);
    }


    public UserKey(int expireSeconds,String prefix) {
        super(prefix);
    }

    //单例 默认过期时间  0  /  prefix
    public static UserKey getByUserId = new UserKey("user_id");


    public static UserKey getByName = new UserKey("role_name");


}
