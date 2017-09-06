package AlgorithmTasks;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Startup {
    public static void main(String[] args){
        IceCream();
    }


    public static void IceCream(){
        Scanner s = new Scanner(System.in);
        int maxNums = Integer.parseInt(s.nextLine());
        int numberOfCombinations = 2;
        int[][] combinations = new int[numberOfCombinations][3];

        for (int i=0; i < numberOfCombinations; i++){
            combinations[i] = Stream.of(s.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        int counter = 0;
        for(int i = 1; i <= maxNums; i++){
            for(int j = 1; j <= maxNums; j++){
                for(int k = 1; k <= maxNums; k++){
                    for(int p = 0; p < combinations.length; p++){
                        if(Math.abs(combinations[p][0] - i) <= 2 && Math.abs(combinations[p][1] - j) <= 2 && Math.abs(combinations[p][2] - k) <= 2){
                            counter++;
                        }
                    }
                }
            }
        }

        System.out.println(counter);

    }
}
