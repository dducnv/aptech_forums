package com.example.forums_backend.utils;

import java.util.Random;

public class GeneratingPassword {

    private static String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    private static String specialCharacters = "_@";
    private static String numbers = "1234567890";

    public static char[] generatePassword(int length){
        String combinedChars =  numbers;
        Random random = new Random();
        char[] password = new char[length];
        password[0] = numbers.charAt(random.nextInt(numbers.length()));
        password[1] = numbers.charAt(random.nextInt(numbers.length()));
        password[2] = numbers.charAt(random.nextInt(numbers.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));
        password[4] = numbers.charAt(random.nextInt(numbers.length()));
        for(int i = 5; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
