package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * Methods to streamline the usage of Servos
 */
public class Servo extends subsystem {
    private com.qualcomm.robotcore.hardware.Servo servo;
    private Telemetry telemetry;

    /**
     * Servo object
     *
     * @param name  Name of the servo in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public Servo(String name, @NonNull HardwareMap hwMap) {
        servo = hwMap.get(com.qualcomm.robotcore.hardware.Servo.class, name);
        this.telemetry = telemetry;
    }

    /**
     * Sets the servo's position to zero
     */
    public void zero() {
        servo.setPosition(0);
    }

    /**
     * Sets the servo's position to zero
     *
     * @param position Integer of target position
     */
    public void setPosition(int position) {
        servo.setPosition(position);
    }
}