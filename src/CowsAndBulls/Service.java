package CowsAndBulls;

import java.util.Random;

public class Service {
    public static char[] GetRandomCombination(int asciiStart, int asciiEnd, int amount){
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
//            boolean isUnique = true;
//            for(int i=0; i<counter;i++){
//                if((int)combination[i] == nextChar) {
//                    isUnique = false;
//                    break;
//                }
//            }
//            if(isUnique){
//                combination[counter] = (char)(nextChar);
//                counter++;
//            }
        }

        return combination;
    }

    public static char[] GetRandomCombination(GameOption option, int count){
        switch(option) {
            case Digits:
                return GetRandomCombination(48, 57, count);
            case LowercaseAlphabet:
                return GetRandomCombination(97, 122, count);
            case UppercaseAlphabet:
                return GetRandomCombination(65, 90, count);
            default:
                throw new IllegalArgumentException("You should choose a valid game option.");

        }
    }

    public static Result GetResult(char[] generated, char[] guess) {
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
