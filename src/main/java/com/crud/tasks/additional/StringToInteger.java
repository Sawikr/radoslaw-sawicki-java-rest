package com.crud.tasks.additional;

public class StringToInteger {

    public static void main(String[] args) {

        String stringNumber = "2555";
        System.out.println("The string is: " + stringNumber);
        Integer integerNumber = convertStringToInteger(stringNumber);
        System.out.println("The string converted to an integer is: " + integerNumber);
    }

    private static Integer convertStringToInteger(String str) {
        int number = 0;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return number;
    }
}
