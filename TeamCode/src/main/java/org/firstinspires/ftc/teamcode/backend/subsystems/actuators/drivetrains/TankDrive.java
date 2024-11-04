package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainMotorControls;

import java.util.Arrays;

/**
 * Object in order to create tank drive for autonomous and teleop
 */
public class TankDrive extends subsystem implements DrivetrainMotorControls {
    private Motor frontLeft, frontRight, backLeft, backRight;

    /**
     * Creates a TankDrive drive Object by putting motors into a sorted array
     *
     * @param motors Four motor Objects in an array
     */
    public TankDrive(Motor[] motors, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
    }

    /**
     * Put power to motors for the left and right sides
     *
     * @param left  Left joystick
     * @param right Right joystick
     */
    public void teleOpDrive(double left, double right) {
        runWithoutEncoder(DTMotors.DRIVETRAIN_BASIC_4);
        frontLeft.setPower(left);
        frontRight.setPower(right);
    }

    /**
     * Set power to motors using a case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param power Power (between -1 and 1)
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
     * Set the target position of the motors using a case switch
     *
     * @param motors  Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param targetPosition Target Position in ticks
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
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void runToPosition(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param motors    Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param wait If you want to wait till the drivetrain is in position or if the code should just continue
     */
    public void RTP(DTMotors motors, boolean wait) {
        Telemetry.Item tankDriveRTPTelemetry = telemetry().addData("TankDrive moving", "");
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
            telemetry().update();
            while (isBusy()) {
            }
        }
        telemetry().removeItem(tankDriveRTPTelemetry);
        telemetry().update();
    }

    /**
     * Sets the mode of the motor to STOP_AND_RESET_ENCODERS using case switch
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
     * Sets the mode of the motor to RUN_WITHOUT_ENCODERS using case switch
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
     * Sets the mode of the motor to RUN_USING_ENCODERS using case switch
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
     * Sets tolerance of motor
     *
     * @param i Tolerance in ticks to consider not busy when running to position
     */
    public void ST(int i) {
        frontLeft.setTolerance(i);
        backLeft.setTolerance(i);
        backRight.setTolerance(i);
        frontRight.setTolerance(i);
    }

    /**
     * Returns whether or not the drivetrain is busy
     *
     * @return isBusy (true or false)
     */
    public boolean isBusy() {
        return frontLeft.isBusy() && backLeft.isBusy() && backRight.isBusy() && frontRight.isBusy();
    }

    /**
     * Driving method used for autonomous using case switch, distance, and power
     *
     * @param direction Direction to drive
     * @param inches    Distance using inches
     * @param speed     Power (between -1 and 1)
     */
    public void drive(DrivetrainHolonomic.Directions direction, double inches, double speed) {
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
                break;
            case BACKWARDS:
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, EaseCommands.inTT_dt(-inches));
                setPower(DTMotors.DRIVETRAIN_BASIC_4, speed);
                runToPosition(DTMotors.DRIVETRAIN_BASIC_4);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                setPower(DTMotors.DRIVETRAIN_BASIC_4, 0);
                break;
            default:
                throw new IllegalArgumentException(direction + " is an invalid direction for TankDrive");
        }
    }
}