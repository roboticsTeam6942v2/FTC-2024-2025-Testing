package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.EaseCommands;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.interfaces.DrivetrainHolonomic;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

import java.util.Arrays;

public class HDrive extends subsystem implements DrivetrainHolonomic {
    private Motor frontLeft, frontRight, backLeft, backRight, midShift;
    private Telemetry telemetry;

    /**
     * Creates a HDrive drive Object by putting motors into a sorted array, and declaring the odd motor out separate
     *
     * @param motors   Four base motor Objects in an array
     * @param midshift The rotated motor Object
     */
    public HDrive(Motor[] motors, Motor midshift, Telemetry telemetry) {
        Arrays.sort(motors); // allows us to ensure motors are in the right order no matter what order the motor array is sent in
        this.backLeft = motors[0];
        this.backRight = motors[1];
        this.frontLeft = motors[2];
        this.frontRight = motors[3];
        this.midShift = midshift;
        midshift.close();
        // close because java is pass by value not pass by reference, if we can come up with a way to pass by reference or object we can change the function to be also able to control individual motors using the motor class
        for (Motor motor : motors) {
            motor.close();
        }
        this.telemetry = telemetry;
    }

    /**
     * Set power to motors for teleOp driving
     *
     * @param y  Driving
     * @param rx Rotation
     * @param x  Strafing
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
     * Set power to motors for teleOp driving, allows for adjustment to speed
     *
     * @param y     Driving
     * @param rx    Rotation
     * @param x     Strafing
     * @param speed Speed reduction, higher reduction means slower speed
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
     * Tell the drivetrain to drive a given direction x amount of inches at a given speed
     *
     * @param direction Direction to travel
     * @param inches    Distance to travel in inches
     * @param speed     Speed from 0-1 for the drivetrain to travel
     */
    @Override
    public void drive(@NonNull Directions direction, double inches, double speed) {
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
     * If the drivetrain is running
     *
     * @return Boolean, true if motors are running
     */
    @Override
    public boolean isBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy() && backLeft.isBusy() && midShift.isBusy();
    }

    /**
     * Sets power to drivetrain motors
     *
     * @param m Motor you want to set power to
     * @param p Power you want the motor to travel at, 0-1
     */
    @Override
    public void SP(@NonNull DTMotors m, double p) {
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
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    @Override
    public void RTP(@NonNull DTMotors m) {
        telemetry.addData("HDrive moving", "");
        telemetry.update();
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
        telemetry.clear();
    }

    /**
     * Set the target position of the motors using a case switch
     *
     * @param m  Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     * @param tp Target Position in ticks
     */
    @Override
    public void STP(@NonNull DTMotors m, int tp) {
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
     * Sets given motors relative ticks to 0, STOP_AND_RESET_ENCODERS
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    @Override
    public void SAR(@NonNull DTMotors m) {
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
     * Sets given motors to RunMode.RUN_WITHOUT_ENCODER
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    @Override
    public void RWE(@NonNull DTMotors m) {
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
     * Sets given motors to RunMode.RUN_USING_ENCODER
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    @Override
    public void RUE(@NonNull DTMotors m) {
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
     * Takes advantage of holonomic drivetrain to travel at abnormal angles
     *
     * @param inches       Inches to travel
     * @param directionDeg Direction in degrees
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
