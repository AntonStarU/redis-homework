import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;


public class RedisStorage {

    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> users;
    private final static String KEY = "homework";

    private double getTs() {
        return new Date().getTime();
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException exc) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(exc.getMessage());
        }
        rKeys = redisson.getKeys();
        users = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void logUsers(int userId) {
        users.add(getTs(), String.valueOf(userId));
    }

    String getLastUser() {
        return users.last();
    }
}
