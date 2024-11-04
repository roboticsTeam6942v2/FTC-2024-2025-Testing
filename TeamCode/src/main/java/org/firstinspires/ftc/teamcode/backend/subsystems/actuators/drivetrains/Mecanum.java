package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;

import java.util.Arrays;

/**
 * This class represents a Mecanum drivetrain for autonomous and teleoperation
 * It allows for control of a holonomic robot using mecanum wheels, enabling movements in multiple directions
 */
public class Mecanum extends subsystem implements DrivetrainHolonomic {
    private Motor frontLeft, frontRight, backLeft, backRight;

    /**
     * Constructs a Mecanum drivetrain object and initializes the motor positions
     * The motor array is sorted to ensure motors are assigned to correct drivetrain positions
     *
     * @param motors    An array containing four motor objects, representing the drivetrain motors
     * @param telemetry The telemetry object for logging data
     */
    public Mecanum(Motor[] motors, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
    }

    /**
     * Controls the drivetrain in teleOp mode, allowing driving, strafing, and rotating
     *
     * @param y  Forward or backward motion
     * @param rx Rotational motion (turning)
     * @param x  Lateral motion (strafing)
     */
    @Override
    public void teleOpDrive(double y, double rx, double x) {
        runWithoutEncoder(DTMotors.DRIVETRAIN_BASIC_4);
        // maintain ratio in case of range clip
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeft.setPower(((y + x * 1.1 + rx) / denominator));
        backLeft.setPower(((y - x * 1.1 + rx) / denominator));
        frontRight.setPower(((y - x * 1.1 - rx) / denominator));
        backRight.setPower(((y + x * 1.1 - rx) / denominator));
    }

    /**
     * Controls the drivetrain in teleOp mode with speed adjustment
     *
     * @param y     Forward or backward motion
     * @param rx    Rotational motion (turning)
     * @param x     Lateral motion (strafing)
     * @param speed Speed adjustment factor; higher value slows down the drivetrain
     */
    @Override
    public void teleOpDrive(double y, double rx, double x, double speed) {
        runWithoutEncoder(DTMotors.DRIVETRAIN_BASIC_4);
        // maintain ratio in case of range clip
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeft.setPower(((y + x * 1.1 + rx) / denominator) / speed);
        backLeft.setPower(((y - x * 1.1 + rx) / denominator) / speed);
        frontRight.setPower(((y - x * 1.1 - rx) / denominator) / speed);
        backRight.setPower(((y + x * 1.1 - rx) / denominator) / speed);
    }

    /**
     * Sets motor power using motor abbreviations and power levels
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param power  Power level (from -1 to 1)
     */
    @Override
    public void setPower(DTMotors motors, double power) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.setPower(power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(power);
                break;
            case FRONT_LEFT:
                frontLeft.setPower(power);
                break;
            case FRONT_RIGHT:
                frontRight.setPower(power);
                break;
            case BACK_LEFT:
                backLeft.setPower(power);
                break;
            case BACK_RIGHT:
                backRight.setPower(power);
                break;
            case FRONT:
                frontLeft.setPower(power);
                frontRight.setPower(power);
                break;
            case BACK:
                backLeft.setPower(power);
                backRight.setPower(power);
                break;
            case LEFT:
                frontLeft.setPower(power);
                backLeft.setPower(power);
                break;
            case RIGHT:
                frontRight.setPower(power);
                backRight.setPower(power);
                break;
        }
    }

    /**
     * Sets the target position for motors using a motor abbreviation and target position in ticks
     *
     * @param motors         Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param targetPosition Target position in ticks
     */
    @Override
    public void setTargetPosition(DTMotors motors, int targetPosition) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.setTargetPosition(targetPosition);
                frontRight.setTargetPosition(targetPosition);
                backLeft.setTargetPosition(targetPosition);
                backRight.setTargetPosition(targetPosition);
                break;
            case FRONT_LEFT:
                frontLeft.setTargetPosition(targetPosition);
                break;
            case FRONT_RIGHT:
                frontRight.setTargetPosition(targetPosition);
                break;
            case BACK_LEFT:
                backLeft.setTargetPosition(targetPosition);
                break;
            case BACK_RIGHT:
                backRight.setTargetPosition(targetPosition);
                break;
            case FRONT:
                frontLeft.setTargetPosition(targetPosition);
                frontRight.setTargetPosition(targetPosition);
                break;
            case BACK:
                backLeft.setTargetPosition(targetPosition);
                backRight.setTargetPosition(targetPosition);
                break;
            case LEFT:
                frontLeft.setTargetPosition(targetPosition);
                backLeft.setTargetPosition(targetPosition);
                break;
            case RIGHT:
                frontRight.setTargetPosition(targetPosition);
                backRight.setTargetPosition(targetPosition);
                break;
        }
    }

    /**
     * Sets motors to run to a specific position
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void runToPosition(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets motors to run to a specific position and optionally waits until the position is reached
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param wait   Whether to wait until the position is reached
     */
    public void RTP(DTMotors motors, boolean wait) {
//        Telemetry.Item mecanumRTPTelemetry = telemetry().addData("Mecanum moving", "");
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.runToPosition();
                frontRight.runToPosition();
                backLeft.runToPosition();
                backRight.runToPosition();
                break;
            case FRONT_LEFT:
                frontLeft.runToPosition();
                break;
            case FRONT_RIGHT:
                frontRight.runToPosition();
                break;
            case BACK_LEFT:
                backLeft.runToPosition();
                break;
            case BACK_RIGHT:
                backRight.runToPosition();
                break;
            case FRONT:
                frontLeft.runToPosition();
                frontRight.runToPosition();
                break;
            case BACK:
                backLeft.runToPosition();
                backRight.runToPosition();
                break;
            case LEFT:
                frontLeft.runToPosition();
                backLeft.runToPosition();
                break;
            case RIGHT:
                frontRight.runToPosition();
                backRight.runToPosition();
                break;
        }
        if (wait) {
//            telemetry().update();
            while (isBusy()) {
            }
        }
//        telemetry().removeItem(mecanumRTPTelemetry);
//        telemetry().update();
    }

    /**
     * Stops and resets the motor encoders using a motor abbreviation
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void stopAndReset(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.stopAndReset();
                frontRight.stopAndReset();
                backLeft.stopAndReset();
                backRight.stopAndReset();
                break;
            case FRONT_LEFT:
                frontLeft.stopAndReset();
                break;
            case FRONT_RIGHT:
                frontRight.stopAndReset();
                break;
            case BACK_LEFT:
                backLeft.stopAndReset();
                break;
            case BACK_RIGHT:
                backRight.stopAndReset();
                break;
            case FRONT:
                frontLeft.stopAndReset();
                frontRight.stopAndReset();
                break;
            case BACK:
                backLeft.stopAndReset();
                backRight.stopAndReset();
                break;
            case LEFT:
                frontLeft.stopAndReset();
                backLeft.stopAndReset();
                break;
            case RIGHT:
                frontRight.stopAndReset();
                backRight.stopAndReset();
                break;
        }
    }

    /**
     * Runs motors without using encoders based on the motor abbreviation
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void runWithoutEncoder(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.runWithoutEncoder();
                frontRight.runWithoutEncoder();
                backLeft.runWithoutEncoder();
                backRight.runWithoutEncoder();
                break;
            case FRONT_LEFT:
                frontLeft.runWithoutEncoder();
                break;
            case FRONT_RIGHT:
                frontRight.runWithoutEncoder();
                break;
            case BACK_LEFT:
                backLeft.runWithoutEncoder();
                break;
            case BACK_RIGHT:
                backRight.runWithoutEncoder();
                break;
            case FRONT:
                frontLeft.runWithoutEncoder();
                frontRight.runWithoutEncoder();
                break;
            case BACK:
                backLeft.runWithoutEncoder();
                backRight.runWithoutEncoder();
                break;
            case LEFT:
                frontLeft.runWithoutEncoder();
                backLeft.runWithoutEncoder();
                break;
            case RIGHT:
                frontRight.runWithoutEncoder();
                backRight.runWithoutEncoder();
                break;
        }
    }

    /**
     * Runs motors using encoders based on the motor abbreviation
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void runUsingEncoder(DTMotors motors) {
        switch (motors) {
            case MIDSHIFT:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case ALL:
            case DRIVETRAIN_BASIC_4:
                frontLeft.runUsingEncoder();
                frontRight.runUsingEncoder();
                backLeft.runUsingEncoder();
                backRight.runUsingEncoder();
                break;
            case FRONT_LEFT:
                frontLeft.runUsingEncoder();
                break;
            case FRONT_RIGHT:
                frontRight.runUsingEncoder();
                break;
            case BACK_LEFT:
                backLeft.runUsingEncoder();
                break;
            case BACK_RIGHT:
                backRight.runUsingEncoder();
                break;
            case FRONT:
                frontLeft.runUsingEncoder();
                frontRight.runUsingEncoder();
                break;
            case BACK:
                backLeft.runUsingEncoder();
                backRight.runUsingEncoder();
                break;
            case LEFT:
                frontLeft.runUsingEncoder();
                backLeft.runUsingEncoder();
                break;
            case RIGHT:
                frontRight.runUsingEncoder();
                backRight.runUsingEncoder();
                break;
        }
    }

    /**
     * Sets the tolerance for considering the motor "not busy" when running to a position
     *
     * @param i Tolerance value in ticks
     */
    public void ST(int i) {
        frontLeft.setTolerance(i);
        backLeft.setTolerance(i);
        backRight.setTolerance(i);
        frontRight.setTolerance(i);
    }

    /**
     * Checks if the drivetrain motors are still busy
     *
     * @return true if the drivetrain is busy, false otherwise
     */
    @Override
    public boolean isBusy() {
        return frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy();
    }

    /**
     * Drives the robot autonomously based on direction, distance in inches, and speed
     *
     * @param direction Direction to drive (FORWARD, BACKWARDS, LEFT, RIGHT, etc.)
     * @param inches    Distance to drive in inches
     * @param speed     Speed of the movement (from -1 to 1)
     */
    @Override
    public void drive(Directions direction, double inches, double speed) {
        stopAndReset(DTMotors.DRIVETRAIN_BASIC_4);
        runUsingEncoder(DTMotors.DRIVETRAIN_BASIC_4);
        switch (direction) {
            case FORWARD:
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case BACKWARDS:
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(-inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case LEFT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(-inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case RIGHT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_FORWARDS_RIGHT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_BACKWARDS_LEFT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(-inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_FORWARDS_LEFT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(0));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            case DIAGONAL_BACKWARDS_RIGHT:
                setTargetPosition(DTMotors.FRONT_LEFT, EaseCommands.inTT_dt(0));
                setTargetPosition(DTMotors.FRONT_RIGHT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.BACK_LEFT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.BACK_RIGHT, EaseCommands.inTT_dt(0));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
            default:
                throw new IllegalArgumentException(direction + " is an invalid direction for Mecanum");
        }
    }

    /**
     * Moves the robot in a specific direction using degrees and distance
     *
     * @param inches       Distance to move in inches
     * @param directionDeg Direction in degrees (0-360)
     */
    public void moveInDirection(double inches, int directionDeg) {
        double directionRad = Math.toRadians(directionDeg);

        double xComponent = Math.cos(directionRad);
        double yComponent = Math.sin(directionRad);

        double frontLeftPower = yComponent + xComponent;
        double frontRightPower = yComponent - xComponent;
        double backLeftPower = yComponent - xComponent;
        double backRightPower = yComponent + xComponent;

        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backLeftPower /= maxPower;
            backRightPower /= maxPower;
        }

        setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(inches));
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        runToPosition(DTMotors.DRIVETRAIN_BASIC_4);

        while (isBusy()) {
        }
        setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
    }

    // need to find a way to stop roadrunnering
    private void rampUpPower(Motor motor, double targetPower, long durationMillis) {
        double currentPower = motor.getPower();
        double steps = 50; // number of ramp steps
        double stepDuration = durationMillis / steps;
        double powerIncrement = (targetPower - currentPower) / steps;

        for (int i = 0; i < steps; i++) {
            double newPower = currentPower + powerIncrement * i;
            motor.setPower(newPower);
            try {
                Thread.sleep((long) stepDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        motor.setPower(targetPower);
    }
}
