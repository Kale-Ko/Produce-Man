package com.kale_ko.snake;

import com.kale_ko.api.java.Console;
import com.kale_ko.api.java.ConsoleColors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings({"IntegerDivisionInFloatingPointContext"})
public class Game {
    public static Map<String, String> grid = new HashMap<>();

    public static Boolean gameOver = false;
    public static Integer score;
    public static Integer width;
    public static Integer height;
    public static Integer consoleSize;
    public static Integer delay;
    public static Integer snakeSpotX;
    public static Integer snakeSpotY;
    public static Integer appleSpotX;
    public static Integer appleSpotY;
    public static String direction = "RIGHT";

    @SuppressWarnings("BusyWait")
    public static void start(Integer width, Integer height, Integer consoleSize, Integer delay) throws IOException,InterruptedException {
        Game.score = 0;
        Game.width = width;
        Game.height = height;
        Game.consoleSize = consoleSize;
        Game.delay = delay;

        int[] random = randomCords();
        appleSpotX = random[0];
        appleSpotY = random[1];
        Console.debug("The apple will be at X:" + appleSpotX + ", Y:" + appleSpotY, 1);

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

                Console.debug("Set X:" + index + " Y:"+ index2 + " to " + type.toLowerCase(), 2);
            }
        }

        render();

        Thread.sleep(delay);

        while (!gameOver) {
            Thread.sleep(delay);
            update();
        }
    }

    public static void update() throws IOException {
        if (snakeSpotX >= width || snakeSpotX < 0 || snakeSpotY >= height || snakeSpotY < 0) {
            gameOver = true;

            Console.log("Game over!");

            return;
        }

        //noinspection SuspiciousNameCombination
        if (get(appleSpotY, appleSpotX).equalsIgnoreCase("HEAD")) {
            score++;

            int[] random = randomCords();
            appleSpotX = random[0];
            appleSpotY = random[1];

            Console.debug("The new apple will be at X:" + appleSpotX + ", Y:" + appleSpotY, 1);

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

        Console.debug("Moved the snake to X:" + snakeSpotX + " Y:" + snakeSpotY, 1);

        render();

        String newdirrection = Console.getInput();
        if (newdirrection.equalsIgnoreCase("up") || newdirrection.equalsIgnoreCase("w")) {
            direction = "UP";
        } else if (newdirrection.equalsIgnoreCase("down") || newdirrection.equalsIgnoreCase("s")) {
            direction = "DOWN";
        } else if (newdirrection.equalsIgnoreCase("left") || newdirrection.equalsIgnoreCase("a")) {
            direction = "LEFT";
        } else if (newdirrection.equalsIgnoreCase("right") || newdirrection.equalsIgnoreCase("d")) {
            direction = "RIGHT";
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

                Console.debug("Rendered X:" + index + " Y:"+ index2 + " as " + type.toLowerCase(), 2);
            }
        }

        Console.log("\n".repeat(Math.max(0, consoleSize - height)) + output.toString());

        Console.debug("Rendered Snake", 1);
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
}