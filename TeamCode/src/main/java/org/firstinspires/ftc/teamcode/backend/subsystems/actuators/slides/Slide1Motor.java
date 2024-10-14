package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.slides;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

import java.util.LinkedList;

public class Slide1Motor extends subsystem {
    private Motor motor;
    int min, max;
    private final double upPower = .7;
    private final double downPower = .3;
    LinkedList<Integer> levels = new LinkedList<>();

    /**
     * Creates a HDrive drive Object by putting motors into a sorted array, and declaring the odd motor out separate
     *
     * @param motor     Motor for slide
     * @param telemetry Telemetry Object
     * @param min       Max ticks to extend
     * @param max       Min ticks, zero position
     */
    public Slide1Motor(Motor motor, Telemetry telemetry, int min, int max) {
        super(telemetry);
        this.motor = motor;
        this.min = min;
        this.max = max;
        motor.close();
    }

    /**
     * If the slide/lift is running
     *
     * @return Boolean, true if motors are running
     */
    public boolean isBusy() {
        return motor.isBusy();
    }

    /**
     * Sets power to the motor
     *
     * @param power Power you want the motor to travel at, 0-1
     */
    public void SP(double power) {
        motor.SP(power);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     */
    public void RTP() {
        motor.RTP();
    }

    /**
     * Set the target position of the motors using a case switch
     */
    public void STP(int targetPosition) {
        motor.STP(targetPosition);
    }

    /**
     * Sets given motors relative ticks to 0, STOP_AND_RESET_ENCODERS
     */
    public void SAR() {
        motor.SAR();
    }

    /**
     * Sets given motors to RunMode.RUN_WITHOUT_ENCODER
     */
    public void RWE() {
        motor.RWE();
    }

    /**
     * Sets given motors to RunMode.RUN_USING_ENCODER
     */
    public void RUE() {
        motor.RUE();
    }

    /**
     * Set the tolerance of the motor, used to determine how close the target position must be to the current position for isBusy() to return false
     *
     * @param ticks Number of ticks
     */
    public void ST(int ticks) {
        motor.ST(ticks);
    }

    /**
     * Returns the current position of the motor Object, Ex frontLeft.GCP();
     *
     * @return The current position of the motor in ticks
     */
    public double GCP() {
        return motor.GCP();
    }

    /**
     * Returns the target position of the motor Object, Ex frontLeft.GTP();
     *
     * @return The target position of the motor in ticks
     */
    public int GTP() {
        return motor.GTP();
    }

    /**
     * Returns the power of the motor Object
     *
     * @return The motor's power as a double
     */
    public double GP() {
        return motor.GP();
    }

    private double powerSetter(int ticks) {
        if (GCP() < ticks)
            return upPower;
        return downPower;
    }

    /**
     * Go to a position of height
     *
     * @param ticks The height in ticks you want travel to
     */
    public void goToPosition(int ticks) {
        goToPosition(ticks, true);
    }

    /**
     * Go to a position of height
     *
     * @param ticks The height in ticks you want travel to
     * @param wait  If you want to wait till you get to position or if the code should just continue
     */
    public void goToPosition(int ticks, boolean wait) {
        telemetry().addData("Moving arm", "");
        STP(GTP() + ticks);
        SP(powerSetter(ticks));
        RTP();
        if (wait) {
            while (isBusy()) {
            }
        }
        SP(downPower);
    }

    /**
     * Adds a level of height to maintain and travel to
     *
     * @param ticks The height in ticks you want to maintain
     */
    public boolean addLevels(int ticks) {
        if (levels.contains(ticks))
            return false;
        levels.add(ticks);
        return true;
    }

    public boolean goToLevel(int level, boolean wait) {
        if (levels.size() > level)
            return false;
        goToPosition(levels.get(level), wait);
        return true;
    }
}
