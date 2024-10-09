package org.firstinspires.ftc.teamcode.backend.subsystems;

import static java.lang.Math.round;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * EaseCommands Object, Group of methods that are fundamental and may be useful in making programming easier
 */
public class EaseCommands {
    private static final Constants c = new Constants();
    // literally just anything to make life easier
    // if imported static you can just use it in your code
    // just call import static org.firstinspires.ftc.teamcode.subsystems.ease_commands.*;

    public static final HashMap<String, int[]> basicColors = new HashMap<>();
    // i have this loaded so that the unneeded ones can be commented out
    static {
        // primary Colors
        basicColors.put("Red", new int[]{255, 0, 0});
        basicColors.put("Green", new int[]{0, 255, 0});
        basicColors.put("Blue", new int[]{0, 0, 255});
        // secondary Colors
        basicColors.put("Yellow", new int[]{255, 255, 0});
        basicColors.put("Cyan", new int[]{0, 255, 255});
        basicColors.put("Magenta", new int[]{255, 0, 255});
        basicColors.put("Orange", new int[]{255, 165, 0});
        basicColors.put("Purple", new int[]{128, 0, 128});
        // neutrals
        basicColors.put("Black", new int[]{0, 0, 0});
        basicColors.put("White", new int[]{255, 255, 255});
        basicColors.put("Gray", new int[]{128, 128, 128});
        basicColors.put("Light Gray", new int[]{211, 211, 211});
        basicColors.put("Dark Gray", new int[]{169, 169, 169});
        basicColors.put("Silver", new int[]{192, 192, 192});
        // other Colors
        basicColors.put("Pink", new int[]{255, 192, 203});
        basicColors.put("Brown", new int[]{165, 42, 42});
        basicColors.put("Maroon", new int[]{128, 0, 0});
        basicColors.put("Olive", new int[]{128, 128, 0});
        basicColors.put("Army Green", new int[]{0, 128, 0});
        basicColors.put("Teal", new int[]{0, 128, 128});
        basicColors.put("Navy", new int[]{0, 0, 128});
    }

    // math based functions

    /**
     * Returns the sum of the list of values
     *
     * @param i Iterable object containing numbers
     * @return Sum of list
     */
    public static <T extends Number> double sumAll(Iterable<T> i) {
        double o = 0;
        for (T x : i) {
            o += x.doubleValue();
        }
        return o;
    }

    /**
     * Returns the smallest value of the list of values
     *
     * @param i Iterable comparable object containing numbers
     * @return Smallest value of the list
     */
    public static <T extends Number & Comparable<T>> T minAll(Iterable<T> i) {
        Iterator<T> iterator = i.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Iterable is empty");
        }
        T o = iterator.next();
        for (T value : i) {
            if (value.compareTo(o) < 0) {
                o = value;
            }
        }
        return o;
    }

    /**
     * Returns the largest value of the list of values
     *
     * @param i Iterable comparable object containing numbers
     * @return Largest value of the list
     */
    public static <T extends Number & Comparable<T>> T maxAll(Iterable<T> i) {
        Iterator<T> iterator = i.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Iterable is empty");
        }

        T o = iterator.next();
        for (T value : i) {
            if (value.compareTo(o) > 0) {
                o = value;
            }
        }
        return o;
    }

    /**
     * Returns the first index of a given value in a list
     *
     * @param flag What we want to find the index of, must be comparable
     * @param arr  Iterable list containing elements
     * @return Index of the value in the list, or -1 if not found
     */
    public static <T extends Comparable<T>> Optional<Integer> findInArr(T flag, Iterable<T> arr) {
        int i = 0;
        for (T x : arr) {
            if (x.compareTo(flag) == 0) {
                return Optional.of(i);
            }
            i++;
        }
        return Optional.ofNullable(null);
    }

    /**
     * Method to calculate the similarity of an RGBA color to basic colors.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     * @return The name of the closest basic color
     */
    public static String findClosestColor(int r, int g, int b) {
        double minDistance = Double.MAX_VALUE;
        String closestColor = "";

        for (Map.Entry<String, int[]> entry : basicColors.entrySet()) {
            int[] color = entry.getValue();
            double distance = Math.sqrt( // euclidean distance
                    Math.pow(r - color[0], 2) +
                            Math.pow(g - color[1], 2) +
                            Math.pow(b - color[2], 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                closestColor = entry.getKey();
            }
        }

        return closestColor;
    }

    public static double calculateColorSimilarity(int[] targetRGB, int r, int g, int b) {
        return Math.sqrt(Math.pow(r - targetRGB[0], 2) + Math.pow(g - targetRGB[1], 2) + Math.pow(b - targetRGB[2], 2));
    }

    /**
     * Returns a unit of measure converted to inches
     *
     * @param i The number you want to convert
     * @param u The unit the number is, "in", "cm", or "mm"
     * @return Index of the value in the array
     */
    public static double toIN(double i, String u) {
        return u.equals("in") ? i : u.equals("cm") ? i / 2.54 : u.equals("mm") ? i / 25.4 : 0;
    }

    /**
     * Returns a value of ticks to travel based of the conversion factor defined in Constants class
     *
     * @param inches The number you want to travel in inches
     * @return Integer of ticks to get to target distance
     */
    public static int inTT_dt(double inches) {
        return (int) round(c.conversion_factor_dt * inches);
    }
    /*
    public static int inTT_linear_slide(double inches){
        return (int)round(c.conversion_factor_linear_slide*inches);
    }*/
}
