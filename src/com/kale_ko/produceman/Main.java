package com.kale_ko.produceman;

import com.kale_ko.api.java.Console;

import java.io.IOException;

public class Main {
    public static String debug = "none";
    public static Boolean autostart = false;
    public static int size = 8;

    public static void main(String[] args) throws IOException, InterruptedException {
        int index = 0;
        for (String string : args) {
            if (string.contentEquals("--size")) {
                size = Integer.parseInt(args[index + 1]);

                Console.log("Set grid size to " + args[index + 1]);
            } else if (string.equalsIgnoreCase("-noboxes")) {
                Game.areBoxes = false;

                Console.log("Disabled boxes");
            } else if (string.equalsIgnoreCase("-boxes")) {
                Game.boxes = Integer.valueOf(args[index + 1]);

                Console.log("Set amount of boxes to " + args[index + 1]);
            } else if (string.equalsIgnoreCase("-autostart")) {
                autostart = true;

                Console.log("Turned on autostart");
            } else if (string.equalsIgnoreCase("-debug")) {
                debug = "low";

                Console.log("Turned on debug");
            } else if (string.contentEquals("--debug")) {
                if (args[index + 1].equalsIgnoreCase("low") || args[index + 1].equalsIgnoreCase("full")) {
                    debug = args[index + 1];

                    Console.log("Set debug to " + args[index + 1]);
                } else {
                    Console.warn(args[index + 1] + " is not a debug level");
                }
            }

            index++;
        }

        if (!autostart) {
            Console.log("Welcome to Produce man, a game where you need to collect the fruit, would you like to learn how to play?");

            if (Console.getInput().equalsIgnoreCase("yes")) {
                Console.log("To play you use either wasd or directionals(left, right, up, down) to move. You can click enter to continue in the same direction");
                Console.log("You need to collect all the apples while avoiding the boxes and walls");

                Console.log("\nClick enter to start");

                Console.getInput();
            }
        }

        Console.log("Ok, here we go..");

        Thread.sleep(300);

        Game.start(size + 1);
    }
}