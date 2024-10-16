package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoEx extends Servo {
    private int degrees;

    public ServoEx(String name, HardwareMap hwMap, Telemetry telemetry, int degrees) {
        super(name, hwMap, telemetry);
        this.degrees = degrees;
        servo.scaleRange(0, degrees);
    }

    @Override
    public void setPosition(double degrees) {
        servo.setPosition(degrees > this.degrees ? this.degrees : (degrees < 0 ? 0 : degrees));
    }
}
