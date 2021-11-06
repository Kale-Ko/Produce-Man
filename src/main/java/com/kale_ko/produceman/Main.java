/**
    @license
    MIT License
    Copyright (c) 2021 Kale Ko
    See https://kaleko.ga/license.txt
*/

package com.kale_ko.produceman;

import com.kale_ko.api.java.Console;
import java.io.IOException;

public class Main {
    public static DebugModes debug = DebugModes.NONE;
    public static Boolean autostart = false;
    public static int size = 8;

    public static void main(String[] args) throws IOException, InterruptedException {
        int index = 0;
        for (String string : args) {
            if (string.contentEquals("--size")) {
                size = Integer.parseInt(args[index + 1]);

                Console.log("Set grid size to " + args[index + 1]);
            } else if (string.equalsIgnoreCase("-noboxes")) {
                Game.boxes = 0;

                Console.log("Disabled boxes");
            } else if (string.equalsIgnoreCase("-boxes")) {
                Game.boxes = Integer.valueOf(args[index + 1]);

                Console.log("Set amount of boxes to " + args[index + 1]);
            } else if (string.equalsIgnoreCase("-autostart")) {
                autostart = true;

                Console.log("Turned on autostart");
            } else if (string.equalsIgnoreCase("-debug")) {
                debug = DebugModes.SOME;

                Console.log("Turned on debug");
            } else if (string.contentEquals("--debug")) {
                if (args[index + 1].equalsIgnoreCase("some")) {
                    debug = DebugModes.SOME;

                    Console.log("Set debug to " + args[index + 1]);
                } else if (args[index + 1].equalsIgnoreCase("all")) {
                    debug = DebugModes.ALL;

                    Console.log("Set debug to " + args[index + 1]);
                } else {
                    Console.warn(args[index + 1] + " is not a debug level. Try none, some, or all");
                }
            }

            index++;
        }

        if (!autostart) {
            Console.log("Welcome to Produce man, a game where you need to collect the fruit and avoid boxes, would you like to learn how to play?");

            if (Console.getInput().equalsIgnoreCase("yes")) {
                Console.log("To play you use wasd to move. You can click enter to continue in the same direction");
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