package com.mongodb.app;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean validateName(String name) {
        return name.trim().length() > 0;
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean validatePhone(String phone) {
        return phone.matches("\\d{10,}");
    }

    public static boolean validateAddress(String address) {
        return address.trim().length() > 0;
    }

    public static String getValidInput(String input, Predicate<String> validator) {
        while (!validator.test(input)) {
            System.out.print("Invalid input. Please try again: ");
            input = new java.util.Scanner(System.in).nextLine();
        }
        return input;
    }
}
