package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools.EaseCommands;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

import java.util.Arrays;

/**
 * Represents an H-Drive drivetrain for a robot.
 * This drivetrain consists of four base motors arranged in a traditional holonomic setup
 * (frontLeft, frontRight, backLeft, backRight), and a fifth motor (midShift) for lateral movement
 */
public class HDrive extends subsystem implements DrivetrainHolonomic {
    private Motor frontLeft, frontRight, backLeft, backRight, midShift;

    /**
     * Constructs an H-Drive object with the specified base motors and a mid-shift motor for lateral movement
     *
     * @param motors    An array of four base Motor objects representing the base of the drivetrain
     * @param midshift  The mid-shift Motor object for lateral movement
     * @param telemetry The telemetry object for displaying data
     */
    public HDrive(Motor[] motors, Motor midshift, Telemetry telemetry) {
        super(telemetry);
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
        this.midShift = midshift;
    }

    /**
     * Controls the drivetrain during TeleOp mode by setting power to the motors for driving, rotation, and strafing
     *
     * @param y  The power for forward/backward movement
     * @param rx The power for rotation
     * @param x  The power for strafing (lateral movement)
     */
    @Override
    public void teleOpDrive(double y, double rx, double x) {
        runWithoutEncoder(DTMotors.ALL);
        frontLeft.setPower(((y + rx)));
        backLeft.setPower(((y + rx)));
        frontRight.setPower(((y - rx)));
        backRight.setPower(((y - rx)));
        midShift.setPower(x * 1.5);
    }

    /**
     * Controls the drivetrain during TeleOp mode with adjustable speed for driving, rotation, and strafing
     *
     * @param y     The power for forward/backward movement
     * @param rx    The power for rotation
     * @param x     The power for strafing (lateral movement)
     * @param speed Speed modifier; a higher value means slower movement
     */
    @Override
    public void teleOpDrive(double y, double rx, double x, double speed) {
        runWithoutEncoder(DTMotors.ALL);
        frontLeft.setPower(((y + rx)) / speed);
        backLeft.setPower(((y + rx)) / speed);
        frontRight.setPower(((y - rx)) / speed);
        backRight.setPower(((y - rx)) / speed);
        midShift.setPower(x * 1.5 / speed);
    }

    /**
     * Drives the robot in a specified direction for a given distance and speed
     *
     * @param direction The direction in which the robot should move
     * @param inches    The distance to move, in inches
     * @param speed     The speed at which the robot should move (0 to 1)
     */
    @Override
    public void drive(Directions direction, double inches, double speed) {
        stopAndReset(DTMotors.ALL);
        runUsingEncoder(DTMotors.ALL);
        int val;

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
            case LEFT:
                setTargetPosition(DTMotors.MIDSHIFT, EaseCommands.inTT_dt(-inches));
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, 0);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            case RIGHT:
                setTargetPosition(DTMotors.MIDSHIFT, EaseCommands.inTT_dt(inches));
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, 0);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            case DIAGONAL_FORWARDS_RIGHT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                setTargetPosition(DTMotors.MIDSHIFT, val);
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, val);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            case DIAGONAL_BACKWARDS_LEFT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                setTargetPosition(DTMotors.MIDSHIFT, val);
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, val);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            case DIAGONAL_FORWARDS_LEFT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                setTargetPosition(DTMotors.MIDSHIFT, -val);
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, val);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            case DIAGONAL_BACKWARDS_RIGHT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                setTargetPosition(DTMotors.MIDSHIFT, -val);
                setTargetPosition(DTMotors.DRIVETRAIN_BASIC_4, val);
                setPower(DTMotors.ALL, speed);
                runToPosition(DTMotors.ALL);
                while (isBusy()) {
                }
                setPower(DTMotors.ALL, 0);
                break;
            default:
                break;
        }
    }

    /**
     * Checks if any of the drivetrain motors are still running
     *
     * @return True if any of the motors are busy, false otherwise
     */
    @Override
    public boolean isBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy() && backLeft.isBusy() && midShift.isBusy();
    }

    /**
     * Sets the power to specific motors in the drivetrain
     *
     * @param motors Motor group or specific motor to set power for
     * @param power  The power level (0 to 1)
     */
    @Override
    public void setPower(DTMotors motors, double power) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.setPower(power);
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.setPower(power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(power);
                break;
            case ALL:
                frontLeft.setPower(power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(power);
                midShift.setPower(power);
                break;
        }
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION
     *
     * @param motors Motor group or specific motor to run to position
     */
    @Override
    public void runToPosition(DTMotors motors) {
        RTP(motors, true);
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION, optionally waiting for completion
     *
     * @param motors Motor group or specific motor to run to position
     * @param wait   Whether to wait for the motors to reach their target position
     */
    public void RTP(DTMotors motors, boolean wait) {
        Telemetry.Item hDriveRTPTelemetry = telemetry().addData("HDrive moving", "");
        switch (motors) {
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
            case MIDSHIFT:
                midShift.runToPosition();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.runToPosition();
                frontRight.runToPosition();
                backLeft.runToPosition();
                backRight.runToPosition();
                break;
            case ALL:
                frontLeft.runToPosition();
                frontRight.runToPosition();
                backLeft.runToPosition();
                backRight.runToPosition();
                midShift.runToPosition();
                break;
        }
        if (wait) {
            telemetry().update();
            while (isBusy()) {
            }
        }
        telemetry().removeItem(hDriveRTPTelemetry);
        telemetry().update();
    }

    /**
     * Sets the target position of a motor group using a case switch
     *
     * @param motors         Motor group to set the target position for
     * @param targetPosition Target position in encoder ticks
     */
    @Override
    public void setTargetPosition(DTMotors motors, int targetPosition) {
        switch (motors) {
            case FRONT_LEFT:
                frontLeft.setTargetPosition(targetPosition);
                break;
            case MIDSHIFT:
                midShift.setTargetPosition(targetPosition);
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.setTargetPosition(targetPosition);
                frontRight.setTargetPosition(targetPosition);
                backLeft.setTargetPosition(targetPosition);
                backRight.setTargetPosition(targetPosition);
                break;
            case ALL:
                frontLeft.setTargetPosition(targetPosition);
                frontRight.setTargetPosition(targetPosition);
                backLeft.setTargetPosition(targetPosition);
                backRight.setTargetPosition(targetPosition);
                midShift.setTargetPosition(targetPosition);
                break;
        }
    }

    /**
     * Resets the encoder values of the specified motors to zero
     *
     * @param motors Motor group or specific motor to reset
     */
    @Override
    public void stopAndReset(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.stopAndReset();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.stopAndReset();
                frontRight.stopAndReset();
                backLeft.stopAndReset();
                backRight.stopAndReset();
                break;
            case ALL:
                frontLeft.stopAndReset();
                frontRight.stopAndReset();
                backLeft.stopAndReset();
                backRight.stopAndReset();
                midShift.stopAndReset();
                break;
        }
    }

    /**
     * Sets the motors to RUN_WITHOUT_ENCODER mode
     *
     * @param motors Motor group or specific motor
     */
    @Override
    public void runWithoutEncoder(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.runWithoutEncoder();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.runWithoutEncoder();
                frontRight.runWithoutEncoder();
                backLeft.runWithoutEncoder();
                backRight.runWithoutEncoder();
                break;
            case ALL:
                frontLeft.runWithoutEncoder();
                frontRight.runWithoutEncoder();
                backLeft.runWithoutEncoder();
                backRight.runWithoutEncoder();
                midShift.runWithoutEncoder();
                break;
        }
    }

    /**
     * Sets the motors to RUN_USING_ENCODER mode
     *
     * @param motors Motor group or specific motor
     */
    @Override
    public void runUsingEncoder(DTMotors motors) {
        switch (motors) {
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
            case MIDSHIFT:
                midShift.runUsingEncoder();
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
            case DRIVETRAIN_BASIC_4:
                frontLeft.runUsingEncoder();
                frontRight.runUsingEncoder();
                backLeft.runUsingEncoder();
                backRight.runUsingEncoder();
                break;
            case ALL:
                frontLeft.runUsingEncoder();
                frontRight.runUsingEncoder();
                backLeft.runUsingEncoder();
                backRight.runUsingEncoder();
                midShift.runUsingEncoder();
                break;
        }
    }

    /**
     * Moves the drivetrain in a given direction, using holonomic properties for angles
     *
     * @param inches       The distance to move in inches
     * @param directionDeg The direction in degrees (0° is forward)
     */
    public void moveInDirection(double inches, int directionDeg) {
        double directionRad = Math.toRadians(directionDeg);

        // Calculate the directional components
        double xComponent = Math.cos(directionRad); // strafing
        double yComponent = Math.sin(directionRad); // driving

        double frontLeftPower = yComponent + xComponent;
        double frontRightPower = yComponent - xComponent;
        double backLeftPower = yComponent - xComponent;
        double backRightPower = yComponent + xComponent;

        double midShiftPower = xComponent * 1.5;

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
        midShift.setPower(midShiftPower);

        runToPosition(DTMotors.ALL);

        while (isBusy()) {
        }

        setPower(DTMotors.ALL, 0);
    }

}
