import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MathGame {
    private static Random random = new Random();
    private int illegalArgumentCount = 0;

    public List<Integer> primeFactors(int number) {
        /*44*/         if (number < 2) {
            /*45*/             ++this.illegalArgumentCount;
            throw new IllegalArgumentException("number is: " + number + ", need >= 2");
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        /*50*/         int i = 2;
        /*51*/         while (i <= number) {
            /*52*/             if (number % i == 0) {
                /*53*/                 result.add(i);
                /*54*/                 number /= i;
                /*55*/                 i = 2;
                continue;
            }
            /*57*/             ++i;
        }
        /*61*/         return result;
    }

    public static void main(String[] args) throws InterruptedException {
        MathGame game = new MathGame();
        while (true) {
            /*16*/             game.run();
            /*17*/             TimeUnit.SECONDS.sleep(1L);
        }
    }

    public void run() throws InterruptedException {
        try {
            /*23*/             int number = random.nextInt() / 10000;
            /*24*/             List<Integer> primeFactors = this.primeFactors(number);
            /*25*/             MathGame.print(number, primeFactors);
        }
        catch (Exception e) {
            /*28*/             System.out.println(String.format("illegalArgumentCount:%3d, ", this.illegalArgumentCount) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        /*34*/         for (int factor : primeFactors) {
            /*35*/             sb.append(factor).append('*');
        }
        /*37*/         if (sb.charAt(sb.length() - 1) == '*') {
            /*38*/             sb.deleteCharAt(sb.length() - 1);
        }
        /*40*/         System.out.println(sb);
    }
}
