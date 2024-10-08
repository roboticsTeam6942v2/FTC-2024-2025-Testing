package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * Methods to streamline the usage of Servos
 */
public class Servos extends subsystem{
    private Servo servo;
    private Telemetry telemetry;

    /**
     * Servo object
     * @param name Name of the servo in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public Servos(String name, @NonNull HardwareMap hwMap){
        servo = hwMap.get(Servo.class, name);
        this.telemetry = telemetry;
    }

    /**
     * Sets the servo's position to zero
     */
    public void zero(){
        servo.setPosition(0);
    }

    /**
     * Sets the servo's position to zero
     * @param position Integer of target position
     */
    public void setPosition(int position){
        servo.setPosition(position);
    }
}