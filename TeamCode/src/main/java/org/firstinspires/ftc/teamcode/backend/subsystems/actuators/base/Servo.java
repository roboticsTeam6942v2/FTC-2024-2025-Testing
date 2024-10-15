package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * Methods to streamline the usage of Servos
 */
public class Servo extends subsystem {
    protected com.qualcomm.robotcore.hardware.Servo servo; // changed to protected to make a version 2, a ServoEx or NormalizedServo, not set on a name yet

    /**
     * Servo object
     *
     * @param name  Name of the servo in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public Servo(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        super(hwMap, telemetry);
        servo = hwMap.get(com.qualcomm.robotcore.hardware.Servo.class, name);
    }

    /**
     * Sets the servo's position to zero
     *
     * @param targetPosition Double of target position
     */
    public void setPosition(double targetPosition) {
        servo.setPosition(targetPosition > 1 ? 1 : (targetPosition < 0 ? 0 : targetPosition));
    }
}