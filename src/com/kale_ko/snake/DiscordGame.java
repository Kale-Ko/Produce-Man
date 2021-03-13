package com.kale_ko.snake;

import com.kale_ko.api.java.Console;
import com.kale_ko.api.java.ConsoleColors;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"IntegerDivisionInFloatingPointContext"})
public class DiscordGame {
    public static Map<String, String> grid = new HashMap<>();

    public static Boolean gameOver = false;
    public static Integer score;
    public static Integer width;
    public static Integer height;
    public static Integer snakeSpotX;
    public static Integer snakeSpotY;
    public static Integer appleSpotX;
    public static Integer appleSpotY;
    public static String direction = "RIGHT";

    public static void start(Integer width, Integer height) {
        DiscordGame.score = 0;
        DiscordGame.width = width;
        DiscordGame.height = height;

        int[] random = randomCords();
        appleSpotX = random[0];
        appleSpotY = random[1];
        sendMessage("debug1", "The apple will be at X:" + appleSpotX + ", Y:" + appleSpotY);

        for (int index = 0; index < width - 1; index++) {
            for (int index2 = 0; index2 < height - 1; index2++) {
                String type = "AIR";
                if (index == appleSpotX && index2 == appleSpotY) type = "APPLE";
                if (index == Math.round(width / 2)  - 1 && index2 == Math.round(height / 2) - 1) {
                    type = "HEAD";

                    snakeSpotX = Math.round(width / 2) - 1;
                    snakeSpotY = Math.round(width / 2) - 1;
                }

                set(index, index2, type);

                sendMessage("debug2", "Set X:" + index + " Y:"+ index2 + " to " + type.toLowerCase());
            }
        }

        render();
    }

    public static void update() {
        //noinspection SuspiciousNameCombination
        if (get(appleSpotY, appleSpotX).equalsIgnoreCase("HEAD")) {
            score++;

            int[] random = randomCords();
            appleSpotX = random[0];
            appleSpotY = random[1];

            sendMessage("debug1", "The new apple will be at X:" + appleSpotX + ", Y:" + appleSpotY);

            set(appleSpotX, appleSpotY, "APPLE");
        }

        set(snakeSpotX, snakeSpotY, "AIR");

        if (direction.equalsIgnoreCase("UP")) {
            snakeSpotY--;
        } else if (direction.equalsIgnoreCase("DOWN")) {
            snakeSpotY++;
        } else if (direction.equalsIgnoreCase("LEFT")) {
            snakeSpotX--;
        } else if (direction.equalsIgnoreCase("RIGHT")) {
            snakeSpotX++;
        }

        set(snakeSpotX, snakeSpotY, "HEAD");

        sendMessage("debug1", "Moved the snake to X:" + snakeSpotX + " Y:" + snakeSpotY);

        render();

        String newdirrection = getInput();
        if (newdirrection.equalsIgnoreCase("up") || newdirrection.equalsIgnoreCase("w")) {
            direction = "UP";
        } else if (newdirrection.equalsIgnoreCase("down") || newdirrection.equalsIgnoreCase("s")) {
            direction = "DOWN";
        } else if (newdirrection.equalsIgnoreCase("left") || newdirrection.equalsIgnoreCase("a")) {
            direction = "LEFT";
        } else if (newdirrection.equalsIgnoreCase("right") || newdirrection.equalsIgnoreCase("d")) {
            direction = "RIGHT";
        }

        if (snakeSpotX > width - 2 || snakeSpotX < 0 || snakeSpotY > height - 2 || snakeSpotY < 0) {
            gameOver = true;

            sendMessage("message", "Game over!");
        }
    }

    public static void render() {
        StringBuilder output = new StringBuilder();
        for (int index = 0; index < width - 1; index++) {
            if (output.length() > 1) output.append("\n");

            for (int index2 = 0; index2 < height - 1; index2++) {
                String type = get(index, index2);

                if (type.equalsIgnoreCase("AIR")) output.append(ConsoleColors.WHITE_BOLD + "\u25A0  ");
                if (type.equalsIgnoreCase("APPLE")) output.append(ConsoleColors.RED_BOLD + "\u25A0  ");
                if (type.equalsIgnoreCase("HEAD")) output.append(ConsoleColors.GREEN_BOLD + "\u25A0  ");
                if (type.equalsIgnoreCase("SNAKE")) output.append(ConsoleColors.GREEN_BOLD + "\u25A0  ");

                sendMessage("debug2", "Rendered X:" + index + " Y:"+ index2 + " as " + type.toLowerCase());
            }
        }

        sendMessage("message", output.toString());

        sendMessage("debug1", "Rendered Snake");
    }

    public static void set(Integer x, Integer y, String type) {
        grid.put(x + "," + y, type);
    }

    public static String get(Integer x, Integer y) {
        return grid.get(y + "," + x);
    }

    public static int[] randomCords() {
        int x = new Random().nextInt(width - 1);
        int y = new Random().nextInt(height - 1);
        if (x == Math.round(width / 2) - 1 && y == Math.round(height / 2) - 1) return randomCords();
        if (get(x, y) != null) {
            if (get(x, y).equalsIgnoreCase("HEAD") || get(x, y).equalsIgnoreCase("SNAKE")) return randomCords();
        }

        int[] cords = new int[2];
        cords[0] = x;
        cords[1] = y;
        return cords;
    }

    public static String getInput() {
        //Wait for discord input

        return null;
    }

    public static void sendMessage(String level, String message) {
        if (level.equalsIgnoreCase("message")) {
            //Send to discord
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