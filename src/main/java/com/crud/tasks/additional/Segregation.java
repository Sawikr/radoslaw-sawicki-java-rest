package com.crud.tasks.additional;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class Segregation {

    static int[] arrOneSolution = {3, 1, 1, 5, 6, 4};
    static Integer[] arrTwoSolution = {3, 1, 1, 5, 6, 4};

    public static int[] descendingSegregation() {
        return arrOneSolution = Arrays.stream(arrOneSolution).boxed()
               .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static int[] ascendingSegregation() {
        return arrOneSolution = IntStream.rangeClosed(1, arrOneSolution.length)
                .map(i -> arrOneSolution[arrOneSolution.length-i]).toArray();
    }

    public static void descendingSegregationTwo() {
        Arrays.stream(arrTwoSolution)
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .forEach(System.out::print);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Arrays.stream(descendingSegregation()).toArray()));
        System.out.println(Arrays.toString(Arrays.stream(ascendingSegregation()).toArray()));
        descendingSegregationTwo();
    }
}