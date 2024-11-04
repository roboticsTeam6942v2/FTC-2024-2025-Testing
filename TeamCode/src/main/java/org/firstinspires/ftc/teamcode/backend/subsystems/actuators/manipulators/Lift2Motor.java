package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.manipulators;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.Constants;

import java.util.LinkedList;

public class Lift2Motor extends subsystem {
    private Motor motor1, motor2;
    public final String name;
    private final int min, max;
    LinkedList<Integer> levels = new LinkedList<>();

    /**
     * Creates a Lift1Motor object, one of these motors must be reversed
     *
     * @param name      Name of the system, used pretty much only for telemetry
     * @param motor1    First Motor for lift
     * @param motor2    Second Motor for lift
     * @param telemetry Telemetry Object
     * @param min       Max ticks to extend
     * @param max       Min ticks, zero position
     */
    public Lift2Motor(String name, Motor motor1, Motor motor2, Telemetry telemetry, int min, int max) {
        super(telemetry);
        this.name = name;
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.min = min;
        this.max = max;
    }

    /**
     * If the slide/lift is running
     *
     * @return Boolean, true if motors are running
     */
    public boolean isBusy() {
        return motor1.isBusy() && motor2.isBusy();
    }

    /**
     * Sets power to the motor
     *
     * @param power Power you want the motor to travel at, 0-1
     */
    public void setPower(double power) {
        motor1.setPower(power);
        motor1.setPower(power);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     */
    public void runToPosition() {
        motor1.runToPosition();
        motor2.runToPosition();
    }

    /**
     * Set the target position of the motors using a case switch
     */
    public void setTargetPosition(int targetPosition) {
        motor1.setTargetPosition(targetPosition > this.max ? this.max : (targetPosition < this.min ? this.min : targetPosition));
        motor2.setTargetPosition(targetPosition > this.max ? this.max : (targetPosition < this.min ? this.min : targetPosition));
    }

    /**
     * Sets given motors relative ticks to 0, STOP_AND_RESET_ENCODERS
     */
    public void stopAndReset() {
        motor1.stopAndReset();
        motor2.stopAndReset();
    }

    /**
     * Sets given motors to RunMode.RUN_WITHOUT_ENCODER
     */
    public void runWithoutEncoder() {
        motor1.runWithoutEncoder();
        motor2.runWithoutEncoder();
    }

    /**
     * Sets given motors to RunMode.RUN_USING_ENCODER
     */
    public void runUsingEncoder() {
        motor1.runUsingEncoder();
        motor2.runUsingEncoder();
    }

    /**
     * Set the tolerance of the motor, used to determine how close the target position must be to the current position for isBusy() to return false
     *
     * @param ticks Number of ticks
     */
    public void setTolerance(int ticks) {
        motor1.setTolerance(ticks);
        motor2.setTolerance(ticks);
    }

    /**
     * Returns the current position of the motor Object, Ex frontLeft.GCP();
     *
     * @return The current position of the motor in ticks
     */
    public int getCurrentPosition() {
        return (int) ((motor1.getCurrentPosition() + motor2.getCurrentPosition()) / 2);
    }

    /**
     * Returns the target position of the motor Object, Ex frontLeft.GTP();
     *
     * @return The target position of the motor in ticks
     */
    public int getTargetPosition() {
        return (int) ((motor1.getTargetPosition() + motor2.getTargetPosition()) / 2);
    }

    /**
     * Returns the power of the motor Object
     *
     * @return The motor's power as a double
     */
    public double getPower() {
        return motor1.getPower();
    }

    private double powerSetter(int ticks) {
        if (getCurrentPosition() < ticks)
            return Constants.upPower;
        return Constants.downPower;
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
        Telemetry.Item slide2MotorTelemetry = telemetry().addData("Moving Slide/Lift: ", isBusy());
        setTargetPosition(getTargetPosition() + ticks);
        setPower(powerSetter(ticks));
        runToPosition();
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        setPower(Constants.downPower);
        telemetry().removeItem(slide2MotorTelemetry);
        telemetry().update();
    }

    /**
     * Adds a level of height to maintain and travel to
     *
     * @param ticks The height in ticks you want to maintain
     * @return Returns false if the level input isnt valid
     */
    public boolean addLevels(int ticks) {
        if (levels.contains(ticks))
            return false;
        levels.add(ticks);
        return true;
    }

    /**
     * Adds a level of height to maintain and travel to
     *
     * @param level The level you have predetermined and want to travel to
     * @param wait  If you would or would like to proceed the code without getting to position or not
     * @return Returns false if the level input isnt valid
     */
    public boolean goToLevel(int level, boolean wait) {
        if (levels.size() > level)
            return false;
        goToPosition(levels.get(level), wait);
        return true;
    }
}
