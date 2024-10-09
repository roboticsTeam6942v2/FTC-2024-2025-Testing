package org.firstinspires.ftc.teamcode.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.EaseCommands;

// add a while not at color, also make color an enum

/**
 * Methods to streamline the usage of ColorSensors
 */
public class ColorSensor extends subsystem {
    private com.qualcomm.robotcore.hardware.ColorSensor colorSensor;
    private Telemetry telemetry;

    /**
     * ColorSensor Object
     * @param name Name of the ColorSensor in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public ColorSensor(String name, @NonNull HardwareMap hwMap, Telemetry telemetry){
        colorSensor = hwMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, name);
        this.telemetry = telemetry;
    }

    /**
     * Returns red value
     * @return Red value
     */
    public double getRed(){
        return colorSensor.red();
    }

    /**
     * Returns blue value
     * @return Blue value
     */
    public double getBlue(){
        return colorSensor.blue();
    }

    /**
     * Returns green value
     * @return Green value
     */
    public double getGreen(){
        return colorSensor.green();
    }

    /**
     * Returns color as string
     * @return Color
     */

    public String getColor() {
        return EaseCommands.findClosestColor(colorSensor.red(), colorSensor.green(), colorSensor.blue()).toString();
    }

    /**
     * Returns whether or not the red value falls within a set boundary
     * @param num1 Smaller value
     * @param num2 Larger value
     * @return Whether or not the value is in the boundary (true or false)
     */ // basically deprecated with new color based methods found in the EaseCommands class
    public boolean redBoundary(int num1, int num2){
        if (colorSensor.red() > num1 && colorSensor.red() < num2){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns whether or not the blue value falls within a set boundary
     * @param num1 Smaller value
     * @param num2 Larger value
     * @return Whether or not the value is in the boundary (true or false)
     */ // basically deprecated with new color based methods found in the EaseCommands class
    public boolean blueBoundary(int num1, int num2) {
        if (colorSensor.blue() > num1 && colorSensor.blue() < num2){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns whether or not the green value falls within a set boundary
     * @param num1 Smaller value
     * @param num2 Larger value
     * @return Whether or not the value is in the boundary (true or false)
     */ // basically deprecated with new color based methods found in the EaseCommands class
    public boolean greenBoundary(int num1, int num2){
        if (colorSensor.green() > num1 && colorSensor.green() < num2){
            return true;
        } else {
            return false;
        }
    }
}