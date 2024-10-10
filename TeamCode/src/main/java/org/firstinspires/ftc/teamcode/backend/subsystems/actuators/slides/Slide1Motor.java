package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.slides;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

public class Slide1Motor extends subsystem {
    private Motor motor;
    private Telemetry telemetry;
    int min, max;

    /**
     * Creates a HDrive drive Object by putting motors into a sorted array, and declaring the odd motor out separate
     *
     * @param motor     Motor for slide
     * @param telemetry Telemetry Object
     * @param min       Max ticks to extend
     * @param max       Min ticks, zero position
     */
    public Slide1Motor(Motor motor, Telemetry telemetry, int min, int max) {
        this.motor = motor;
        this.telemetry = telemetry;
        this.min = min;
        this.max = max;
        motor.close();
    }

    public boolean isBusy() {
        return motor.isBusy();
    }

    public void SP(int p) {
        motor.SP(p);
    }

    public void RTP() {
        motor.RTP();
    }

    public void STP(int tp) {
        motor.STP(tp);
    }

    public void SAR() {
        motor.SAR();
    }

    public void RWE() {
        motor.RWE();
    }

    public void RUE() {
        motor.RUE();
    }
}
