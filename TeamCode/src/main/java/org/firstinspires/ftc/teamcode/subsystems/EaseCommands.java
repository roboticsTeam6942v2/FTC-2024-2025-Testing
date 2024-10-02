package org.firstinspires.ftc.teamcode.subsystems;

import static java.lang.Math.round;
import java.util.Iterator;

/**
 * EaseCommands Object, Group of methods that are fundamental and may be useful in making programming easier
 */
public class EaseCommands {
    private static final Constants c = new Constants();
    // literally just anything to make life easier
    // if imported static you can just use it in your code
    // just call import static org.firstinspires.ftc.teamcode.subsystems.ease_commands.*;

    // math based functions
    /**
     * Returns the sum of the list of values
     * @param i Iterable object containing numbers
     * @return Sum of list
     */
    public static <T extends Number> double sumAll(Iterable<T> i) {
        double o = 0;
        for (T x : i) {
            o+=x.doubleValue();
        }
        return o;
    }
    /**
     * Returns the smallest value of the list of values
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
    // array based functions
    // findInArr assumes there is only once entry with that value
    /**
     * Returns the first index of a given value in an array
     * @param flag What we want to find the index of
     * @param arr Array containing numbers
     * @return Index of the value in the array
     */
    public double findInArr(double flag, double[] arr) {
        int i = 0;
        for (double x : arr) {
            if (x == flag) {
                break;
            }
            i++;
        }
        return i;
    }
    /**
     * Returns the first index of a given value in a list
     * @param flag  What we want to find the index of, must be comparable
     * @param arr   Iterable list containing elements
     * @return      Index of the value in the list, or -1 if not found
     */
    public static <T extends Comparable<T>> int findInArr(T flag, Iterable<T> arr) {
        int i = 0;
        for (T x : arr) {
            if (x.compareTo(flag) == 0) {
                return i;
            }
            i++;
        }
        throw new IllegalArgumentException("Value not found in array");
    }
    /**
     * Returns a unit of measure converted to inches
     * @param i The number you want to convert
     * @param u The unit the number is, "in", "cm", or "mm"
     * @return Index of the value in the array
     */
    public static double toIN(double i, String u) {
        return u.equals("in")?i:u.equals("cm")?i/2.54:u.equals("mm")?i/25.4:0;
    }
    /**
     * Returns a value of ticks to travel based of the conversion factor defined in Constants class
     * @param inches The number you want to travel in inches
     * @return Integer of ticks to get to target distance
     */
    public static int inTT_dt(double inches){
        return (int)round(c.conversion_factor_dt*inches);
    }
    /*
    public static int inTT_linear_slide(double inches){
        return (int)round(c.conversion_factor_linear_slide*inches);
    }*/
}
