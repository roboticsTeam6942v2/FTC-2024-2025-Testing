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
     * @param motors   An array of four base Motor objects representing the base of the drivetrain
     * @param midshift The mid-shift Motor object for lateral movement
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
        RWE(DTMotors.all);
        frontLeft.SP(((y + rx)));
        backLeft.SP(((y + rx)));
        frontRight.SP(((y - rx)));
        backRight.SP(((y - rx)));
        midShift.SP(x * 1.5);
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
        RWE(DTMotors.all);
        frontLeft.SP(((y + rx)) / speed);
        backLeft.SP(((y + rx)) / speed);
        frontRight.SP(((y - rx)) / speed);
        backRight.SP(((y - rx)) / speed);
        midShift.SP(x * 1.5 / speed);
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
        SAR(DTMotors.all);
        RUE(DTMotors.all);
        int val;

        switch (direction) {
            case FORWARD:
                STP(DTMotors.dt, EaseCommands.inTT_dt(inches));
                SP(DTMotors.dt, speed);
                RTP(DTMotors.dt);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                SP(DTMotors.dt, 0);
                break;
            case BACKWARDS:
                STP(DTMotors.dt, EaseCommands.inTT_dt(-inches));
                SP(DTMotors.dt, speed);
                RTP(DTMotors.dt);
                while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
                }
                SP(DTMotors.dt, 0);
                break;
            case LEFT:
                STP(DTMotors.m, EaseCommands.inTT_dt(-inches));
                STP(DTMotors.dt, 0);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
                break;
            case RIGHT:
                STP(DTMotors.m, EaseCommands.inTT_dt(inches));
                STP(DTMotors.dt, 0);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
                break;
            case DIAGONAL_FORWARDS_RIGHT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.m, val);
                STP(DTMotors.dt, val);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
                break;
            case DIAGONAL_BACKWARDS_LEFT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.m, val);
                STP(DTMotors.dt, val);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
                break;
            case DIAGONAL_FORWARDS_LEFT:
                val = EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.m, -val);
                STP(DTMotors.dt, val);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
                break;
            case DIAGONAL_BACKWARDS_RIGHT:
                val = -EaseCommands.inTT_dt(inches / (Math.sqrt(2)));
                STP(DTMotors.m, -val);
                STP(DTMotors.dt, val);
                SP(DTMotors.all, speed);
                RTP(DTMotors.all);
                while (isBusy()) {
                }
                SP(DTMotors.all, 0);
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
     * @param m Motor group or specific motor to set power for
     * @param p The power level (0 to 1)
     */
    @Override
    public void SP(DTMotors m, double p) {
        switch (m) {
            case fl:
                frontLeft.SP(p);
                break;
            case fr:
                frontRight.SP(p);
                break;
            case bl:
                backLeft.SP(p);
                break;
            case br:
                backRight.SP(p);
                break;
            case m:
                midShift.SP(p);
                break;
            case f:
                frontLeft.SP(p);
                frontRight.SP(p);
                break;
            case b:
                backLeft.SP(p);
                backRight.SP(p);
                break;
            case l:
                frontLeft.SP(p);
                backLeft.SP(p);
                break;
            case r:
                frontRight.SP(p);
                backRight.SP(p);
                break;
            case dt:
                frontLeft.SP(p);
                frontRight.SP(p);
                backLeft.SP(p);
                backRight.SP(p);
                break;
            case all:
                frontLeft.SP(p);
                frontRight.SP(p);
                backLeft.SP(p);
                backRight.SP(p);
                midShift.SP(p);
                break;
        }
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION
     *
     * @param m Motor group or specific motor to run to position
     */
    @Override
    public void RTP(DTMotors m) {
        RTP(m, true);
    }

    /**
     * Sets the target position for the drivetrain motors to RUN_TO_POSITION, optionally waiting for completion
     *
     * @param m    Motor group or specific motor to run to position
     * @param wait Whether to wait for the motors to reach their target position
     */
    public void RTP(DTMotors m, boolean wait) {
        Telemetry.Item hDriveRTPTelemetry = telemetry().addData("HDrive moving", "");
        switch (m) {
            case fl:
                frontLeft.RTP();
                break;
            case fr:
                frontRight.RTP();
                break;
            case bl:
                backLeft.RTP();
                break;
            case br:
                backRight.RTP();
                break;
            case m:
                midShift.RTP();
                break;
            case f:
                frontLeft.RTP();
                frontRight.RTP();
                break;
            case b:
                backLeft.RTP();
                backRight.RTP();
                break;
            case l:
                frontLeft.RTP();
                backLeft.RTP();
                break;
            case r:
                frontRight.RTP();
                backRight.RTP();
                break;
            case dt:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                break;
            case all:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                midShift.RTP();
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
     * @param m  Motor group to set the target position for
     * @param tp Target position in encoder ticks
     */
    @Override
    public void STP(DTMotors m, int tp) {
        switch (m) {
            case fl:
                frontLeft.STP(tp);
                break;
            case m:
                midShift.STP(tp);
                break;
            case fr:
                frontRight.STP(tp);
                break;
            case bl:
                backLeft.STP(tp);
                break;
            case br:
                backRight.STP(tp);
                break;
            case f:
                frontLeft.STP(tp);
                frontRight.STP(tp);
                break;
            case b:
                backLeft.STP(tp);
                backRight.STP(tp);
                break;
            case l:
                frontLeft.STP(tp);
                backLeft.STP(tp);
                break;
            case r:
                frontRight.STP(tp);
                backRight.STP(tp);
                break;
            case dt:
                frontLeft.STP(tp);
                frontRight.STP(tp);
                backLeft.STP(tp);
                backRight.STP(tp);
                break;
            case all:
                frontLeft.STP(tp);
                frontRight.STP(tp);
                backLeft.STP(tp);
                backRight.STP(tp);
                midShift.STP(tp);
                break;
        }
    }

    /**
     * Resets the encoder values of the specified motors to zero
     *
     * @param m Motor group or specific motor to reset
     */
    @Override
    public void SAR(DTMotors m) {
        switch (m) {
            case fl:
                frontLeft.SAR();
                break;
            case fr:
                frontRight.SAR();
                break;
            case bl:
                backLeft.SAR();
                break;
            case br:
                backRight.SAR();
                break;
            case m:
                midShift.SAR();
                break;
            case f:
                frontLeft.SAR();
                frontRight.SAR();
                break;
            case b:
                backLeft.SAR();
                backRight.SAR();
                break;
            case l:
                frontLeft.SAR();
                backLeft.SAR();
                break;
            case r:
                frontRight.SAR();
                backRight.SAR();
                break;
            case dt:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                break;
            case all:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                midShift.SAR();
                break;
        }
    }

    /**
     * Sets the motors to RUN_WITHOUT_ENCODER mode
     *
     * @param m Motor group or specific motor
     */
    @Override
    public void RWE(DTMotors m) {
        switch (m) {
            case fl:
                frontLeft.RWE();
                break;
            case fr:
                frontRight.RWE();
                break;
            case bl:
                backLeft.RWE();
                break;
            case br:
                backRight.RWE();
                break;
            case m:
                midShift.RWE();
                break;
            case f:
                frontLeft.RWE();
                frontRight.RWE();
                break;
            case b:
                backLeft.RWE();
                backRight.RWE();
                break;
            case l:
                frontLeft.RWE();
                backLeft.RWE();
                break;
            case r:
                frontRight.RWE();
                backRight.RWE();
                break;
            case dt:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                break;
            case all:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                midShift.RWE();
                break;
        }
    }

    /**
     * Sets the motors to RUN_USING_ENCODER mode
     *
     * @param m Motor group or specific motor
     */
    @Override
    public void RUE(DTMotors m) {
        switch (m) {
            case fl:
                frontLeft.RUE();
                break;
            case fr:
                frontRight.RUE();
                break;
            case bl:
                backLeft.RUE();
                break;
            case br:
                backRight.RUE();
                break;
            case m:
                midShift.RUE();
                break;
            case f:
                frontLeft.RUE();
                frontRight.RUE();
                break;
            case b:
                backLeft.RUE();
                backRight.RUE();
                break;
            case l:
                frontLeft.RUE();
                backLeft.RUE();
                break;
            case r:
                frontRight.RUE();
                backRight.RUE();
                break;
            case dt:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                break;
            case all:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                midShift.RUE();
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

        STP(DTMotors.dt, EaseCommands.inTT_dt(inches));
        frontLeft.SP(frontLeftPower);
        frontRight.SP(frontRightPower);
        backLeft.SP(backLeftPower);
        backRight.SP(backRightPower);
        midShift.SP(midShiftPower);

        RTP(DTMotors.all);

        while (isBusy()) {
        }

        SP(DTMotors.all, 0);
    }

}
