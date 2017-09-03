import java.util.Objects;
import java.util.Scanner;

public class CowsAndBulls {
    public static void main(String [] args)
    {
        System.out.println("Guess until you win or 0 for exit");
        Scanner s = new Scanner(System.in);
        char[] combination = Service.GetRandomCombination(GameOption.LowercaseAlphabet, 4);
        System.out.println(new String(combination));
        while(true){

            System.out.println("Gimme a guess: ");
            String input = s.nextLine();
            if(Objects.equals(input, "0")){
                System.out.println("Adiós");
                break;
            }

            Result result = Service.GetResult(combination, input.toCharArray());
            System.out.printf("%d Bulls and %d Cows\n", result.getBulls(), result.getCows());
            if(result.isSucceess()){
                System.out.println("GREAT! You win.. nothing. Cya!");
                break;
            }
        }
    }


}