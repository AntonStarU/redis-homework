import java.util.Random;

public class Main {

    private static int id = 1;
    private static int countUsers = 20;
    private static int number = 0;
    private static RedisStorage redis = new RedisStorage();

    public static void main(String[] args) throws InterruptedException {

        redis.init();

        while (true) {
            for (int userId = id; userId <= countUsers; userId++) {
                if (number == 10) {
                    int userRandom = new Random().nextInt(1, countUsers + 1);
                    redis.logUsers(userRandom);
                    userPaysService();
                    number = 0;
                }
                redis.logUsers(userId);
                System.out.println("— На главной странице показываем пользователя " + redis.getLastUser());
                number++;
            }
            id = 1;
            Thread.sleep(1000);
        }
    }

    public static void userPaysService() {
        String str = String.format("> Пользователь %s оплатил платную услугу", redis.getLastUser());
        System.out.println(str);
    }
}
