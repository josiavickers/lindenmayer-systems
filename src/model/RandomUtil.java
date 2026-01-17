package model;

import java.util.Random;

public class RandomUtil {

    public static double getRandomInRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }

        Random random = new Random();
        return min + (random.nextDouble() * (max - min));
    }

    public static void main(String[] args) {
        double number = getRandomInRange(10.0, 20.0);
        System.out.println("Random number: " + number);
    }
}

