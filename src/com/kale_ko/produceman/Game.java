package com.kale_ko.produceman;

import com.kale_ko.api.java.Console;
import com.kale_ko.api.java.ConsoleColors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"IntegerDivisionInFloatingPointContext", "SuspiciousNameCombination"})
public class Game {
    public static Map<String, String> grid = new HashMap<>();

    public static Integer score;
    public static Integer size;
    public static Boolean areBoxes = true;
    public static Integer boxes = 5;
    public static Boolean gameOver = false;
    public static String direction = "RIGHT";
    public static Integer playerSpotX;
    public static Integer playerSpotY;
    public static Integer pointSpotX;
    public static Integer pointSpotY;

    public static void start(Integer size) throws IOException {
        Game.score = 0;
        Game.size = size;

        int[] random = randomCords();
        pointSpotX = random[0];
        pointSpotY = random[1];
        sendMessage("debug1", "The point will be at X:" + pointSpotX + ", Y:" + pointSpotY);

        for (int index = 0; index < size - 1; index++) {
            for (int index2 = 0; index2 < size - 1; index2++) {
                String type = "AIR";
                if (index == pointSpotX && index2 == pointSpotY) type = "POINT";
                if (index == Math.round(size / 2)  - 1 && index2 == Math.round(size / 2) - 1) {
                    type = "PLAYER";

                    playerSpotX = Math.round(size / 2) - 1;
                    playerSpotY = Math.round(size / 2) - 1;
                }

                set(index, index2, type);

                sendMessage("debug2", "Set X:" + index + " Y:"+ index2 + " to " + type.toLowerCase());
            }
        }

         if (areBoxes) {
             for (int index = 0; index < boxes; index++) {
                 int rndX = new Random().nextInt(size - 1);
                 int rndY = new Random().nextInt(size - 1);

                 Console.log(rndX + "  " + rndY);
                 if (get(rndX, rndY).equalsIgnoreCase("AIR")) {
                     set(rndX, rndY, "BOX");
                 } else {
                     index--;
                 }
             }
         }

        render();

        update();
    }

    public static void update() throws IOException {
        if (get(pointSpotY, pointSpotX).equalsIgnoreCase("PLAYER")) {
            score++;

            int[] random = randomCords();
            pointSpotX = random[0];
            pointSpotY = random[1];

            sendMessage("debug1", "The new point will be at X:" + pointSpotX + ", Y:" + pointSpotY);

            set(pointSpotX, pointSpotY, "POINT");

            render();
        }

        String newdirrection = getInput();
        if (newdirrection.equalsIgnoreCase("up") || newdirrection.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("DOWN")) {
            direction = "UP";
        } else if (newdirrection.equalsIgnoreCase("down") || newdirrection.equalsIgnoreCase("s")&& !direction.equalsIgnoreCase("UP")) {
            direction = "DOWN";
        } else if (newdirrection.equalsIgnoreCase("left") || newdirrection.equalsIgnoreCase("a")&& !direction.equalsIgnoreCase("RIGHT")) {
            direction = "LEFT";
        } else if (newdirrection.equalsIgnoreCase("right") || newdirrection.equalsIgnoreCase("d")&& !direction.equalsIgnoreCase("LEFT")) {
            direction = "RIGHT";
        }

        set(playerSpotX, playerSpotY, "AIR");

        if (direction.equalsIgnoreCase("UP")) {
            playerSpotY--;
        } else if (direction.equalsIgnoreCase("DOWN")) {
            playerSpotY++;
        } else if (direction.equalsIgnoreCase("LEFT")) {
            playerSpotX--;
        } else if (direction.equalsIgnoreCase("RIGHT")) {
            playerSpotX++;
        }

        if (get(playerSpotY, playerSpotX).equalsIgnoreCase("BOX") || playerSpotX > size - 2 || playerSpotX < 0 || playerSpotY > size - 2 || playerSpotY < 0) {
            gameOver = true;

            sendMessage("message", "Game over!");
            sendMessage("message", "Your score was " + score);

            return;
        }

        set(playerSpotX, playerSpotY, "PLAYER");

        sendMessage("debug1", "Moved the snake to X:" + playerSpotX + " Y:" + playerSpotY);

        render();

        update();
    }

    public static void render() {
        StringBuilder output = new StringBuilder();
        for (int index = 0; index < size - 1; index++) {
            if (output.length() > 1) output.append(ConsoleColors.RESET + "\n");

            for (int index2 = 0; index2 < size - 1; index2++) {
                String type = get(index, index2);
                Console.debug(type, 1);

                if (type.equalsIgnoreCase("AIR")) output.append(ConsoleColors.BLUE_BOLD + ConsoleColors.BLUE_BACKGROUND + "   ");
                if (type.equalsIgnoreCase("BOX")) output.append(ConsoleColors.BLACK_BOLD + ConsoleColors.BLACK_BACKGROUND + "   ");
                if (type.equalsIgnoreCase("POINT")) output.append(ConsoleColors.YELLOW_BOLD + ConsoleColors.YELLOW_BACKGROUND + "   ");
                if (type.equalsIgnoreCase("PLAYER")) output.append(ConsoleColors.GREEN_BOLD + ConsoleColors.GREEN_BACKGROUND + "   ");

                sendMessage("debug2", "Rendered X:" + index + " Y:"+ index2 + " as " + type.toLowerCase());
            }
        }

        sendMessage("message", "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + output.toString() + ConsoleColors.RESET);
        sendMessage("message", "Score: " + score + "   Direction: " + direction.toLowerCase());

        sendMessage("debug1", "Rendered Player");
    }

    public static void set(Integer x, Integer y, String type) {
        grid.put(x + "," + y, type);
    }

    public static String get(Integer x, Integer y) {
        return grid.get(y + "," + x);
    }

    public static int[] randomCords() {
        int x = new Random().nextInt(size - 1);
        int y = new Random().nextInt(size - 1);
        if (x == Math.round(size / 2) - 1 && y == Math.round(size / 2) - 1) return randomCords();

        if (playerSpotX != null && playerSpotY != null) {
            if (x == playerSpotX && y == playerSpotY) return randomCords();
        }

        int[] cords = new int[2];
        cords[0] = x;
        cords[1] = y;
        return cords;
    }

    public static String getInput() throws IOException {
        return Console.getInput();
    }

    public static void sendMessage(String level, String message) {
        if (level.equalsIgnoreCase("message")) {
            Console.log(message);
        } else if (level.equalsIgnoreCase("warn")) {
            Console.warn(message);
        } else if (level.equalsIgnoreCase("error")) {
            Console.error(message);
        } else if (level.equalsIgnoreCase("debug1")) {
            Console.debug(message, 1);
        } else if (level.equalsIgnoreCase("debug2")) {
            Console.debug(message, 2);
        } else {
            Console.error(level + " is not a message level");
        }
    }
}