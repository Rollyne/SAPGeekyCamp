import java.util.Objects;
import java.util.Scanner;

public class CowsAndBulls {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Choose what you want to guess\n1. Digits\n2. Lowercase Alphabet\n3. Uppercase alphabet\n>>");

        GameOption option = null;
        boolean invalid = true;
        while (invalid) {
            int menuSelect = s.nextInt();
            switch (menuSelect) {

                case 1:
                    option = GameOption.Digits;
                    break;
                case 2:
                    option = GameOption.LowercaseAlphabet;
                    break;
                case 3:
                    option = GameOption.UppercaseAlphabet;
                    break;
                default:
                    System.out.println("Invalid option.");
                    continue;
            }
            invalid = false;
        }

        int count = 0;
        System.out.print("How many characters do you dare to guess?: ");
        while(count == 0){
            count = s.nextInt();
        }
        s.nextLine();

        char[] combination = Service.GetRandomCombination(option, count);
        System.out.println("Guess until you win or 0 for exit");
        //System.out.println(new String(combination));
        while (true) {

            System.out.println("Gimme a guess: ");
            String input = s.nextLine();
            if (Objects.equals(input, "0")) {
                System.out.println("Adiós");
                break;
            }

            Result result = Service.GetResult(combination, input.toCharArray());
            System.out.printf("%d Bulls and %d Cows\n", result.getBulls(), result.getCows());
            if (result.isSucceess()) {
                System.out.println("GREAT! You win.. nothing. Cya!");
                break;
            }
        }
    }
}