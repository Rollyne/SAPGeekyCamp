import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class CowsAndBulls {
    public static void main(String [] args)
    {
        System.out.println("Guess until you win or 0 for exit");
        Scanner s = new Scanner(System.in);
        char[] number = getRandomNumber();

        while(true){

            System.out.println("Gimme a guess: ");
            String input = s.nextLine();
            if(Objects.equals(input, "0")){
                System.out.println("Adiós");
                break;
            }

            int[] result = getResult(number, input.toCharArray());
            System.out.printf("%d Bulls and %d Cows\n", result[0], result[1]);
            if(result[0] == 4){
                System.out.println("GREAT! You win.. nothing. Cya!");
                break;
            }
        }
    }

    private static char[] getRandomNumber(){
        Random generator = new Random();

        char[] number = new char[4];
        int counter = 0;
        while(counter < 4){
            int nextNum = generator.nextInt(9);
            boolean isUnique = true;
            for(int i=0; i<counter;i++){
                if(number[i]-'0' == nextNum) {
                    isUnique = false;
                    break;
                }
            }
            if(isUnique){
                number[counter] = (char)(nextNum+48);
                counter++;
            }
        }

        return number;
    }

    private static int[] getResult(char[] generated, char[] guess) {
        int bullsCount=0;
        int cowsCount =0;
        for(int i=0; i<generated.length; i++){
            char actualNum = generated[i];
            char guessedNum = guess[i];

            if(actualNum==guessedNum)
                bullsCount++;
            else{
                for (int j = 0; j < generated.length; j++){
                    if(generated[j] == guessedNum){
                        cowsCount++;
                        break;
                    }
                }
            }
        }

        return new int[]{bullsCount, cowsCount};
    }
}