package com.kale_ko.snake;

import com.kale_ko.api.java.Console;

import java.io.IOException;

public class Main {
    public static Boolean autostart = false;
    public static String debug = "none";

    public static void main(String[] args) throws IOException,InterruptedException{
        int index = 0;
        for (String string : args) {
            if (string.equalsIgnoreCase("-debug")) {
                debug = "full";
            } else if (string.contentEquals("--debug")) {
                if (args[index + 1].equalsIgnoreCase("low") || args[index + 1].equalsIgnoreCase("full")) {
                    debug = args[index + 1];
                    Console.log("Set debug to " + args[index + 1]);
                } else {
                    Console.warn(args[index + 1] + " is not a debug level");
                }
            } else if (string.equalsIgnoreCase("-autostart")) {
                autostart = true;
            }

            index++;
        }

        if (!autostart) Console.log("Hi, would you like to start?");

        String input = "";
        if (!autostart) input = Console.getInput();
        if (autostart || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("yeah") || input.equalsIgnoreCase("ok")) {
            Console.log("Here we go!");

            Game.start(6, 6, 11, 300);
        } else {
            Console.log("Ok, bye bye");
        }
    }
}