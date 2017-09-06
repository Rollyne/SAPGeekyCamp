package CowsAndBulls;

import java.util.Random;

public class Service {
    public static char[] getRandomCombination(int asciiStart, int asciiEnd, int amount){
        Random generator = new Random();
        asciiEnd+=1;
        char[] combination = new char[amount];
        int counter = 0;

        boolean[] isAlreadyIn = new boolean[asciiEnd-asciiStart];

        while(counter < amount){
            int nextChar = generator.nextInt(asciiEnd-asciiStart) + asciiStart;
            if(!isAlreadyIn[nextChar-asciiStart]){
                isAlreadyIn[nextChar-asciiStart] = true;
                combination[counter] = (char)(nextChar);
                counter++;
            }
        }

        return combination;
    }

    public static char[] getRandomCombination(GameOption option, int count){
        switch(option) {
            case DIGITS:
                return getRandomCombination('1', '9', count);
            case LOWERCASE_ALPHABET:
                return getRandomCombination('a', 'z', count);
            case UPPERCASE_ALPHABET:
                return getRandomCombination('A', 'Z', count);
            default:
                throw new IllegalArgumentException("You should choose a valid game option.");

        }
    }

    public static Result getResult(char[] generated, char[] guess) {
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

        return new Result(cowsCount, bullsCount, bullsCount == generated.length);
    }
}
