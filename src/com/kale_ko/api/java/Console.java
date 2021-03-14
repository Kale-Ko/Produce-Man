package com.kale_ko.api.java;

import com.kale_ko.produceman.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    public static void log(String message) {
        System.out.println(ConsoleColors.RESET + message);
    }

    public static void warn(String message) {
        System.out.println(ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD + "[Warn] " + ConsoleColors.RESET + ConsoleColors.YELLOW + message);
    }

    public static void error(String message) {
        System.out.println(ConsoleColors.RESET + ConsoleColors.RED_BOLD + "[Error] " + ConsoleColors.RESET + ConsoleColors.RED + message);
    }

    public static void debug(String message, int level) {
        if (level == 2 && Main.debug.equalsIgnoreCase("full")) {
            System.out.println(ConsoleColors.RESET + ConsoleColors.PURPLE_BOLD + "[Debug] " + ConsoleColors.RESET + ConsoleColors.PURPLE + message);
        } else if (level == 1 && Main.debug.equalsIgnoreCase("low") || Main.debug.equalsIgnoreCase("full"))  {
            System.out.println(ConsoleColors.RESET + ConsoleColors.PURPLE_BOLD + "[Debug] " + ConsoleColors.RESET + ConsoleColors.PURPLE + message);
        }
    }

    public static String getInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine();
    }
}