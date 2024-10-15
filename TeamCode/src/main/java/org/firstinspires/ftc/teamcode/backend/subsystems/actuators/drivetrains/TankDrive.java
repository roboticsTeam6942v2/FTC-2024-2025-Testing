package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.EaseCommands;
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
        RWE(DTMotors.dt);
        frontLeft.SP(left);
        frontRight.SP(right);
    }

    /**
     * Set power to motors using a case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param p Power (between -1 and 1)
     */
    @Override
    public void SP(@NonNull DTMotors m, double p) {
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.SP(p);
                frontRight.SP(p);
                backLeft.SP(p);
                backRight.SP(p);
                break;
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
        }
    }

    /**
     * Set the target position of the motors using a case switch
     *
     * @param m  Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param tp Target Position in ticks
     */
    @Override
    public void STP(@NonNull DTMotors m, int tp) {
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.STP(tp);
                frontRight.STP(tp);
                backLeft.STP(tp);
                backRight.STP(tp);
                break;
            case fl:
                frontLeft.STP(tp);
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
        }
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RTP(@NonNull DTMotors m) {
        RTP(m, true);
    }

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param m    Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param wait If you want to wait till the drivetrain is in position or if the code should just continue
     */
    public void RTP(@NonNull DTMotors m, boolean wait) {
        Telemetry.Item tankDriveRTPTelemetry = telemetry().addData("TankDrive moving", "");
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.RTP();
                frontRight.RTP();
                backLeft.RTP();
                backRight.RTP();
                break;
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
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void SAR(@NonNull DTMotors m) {
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.SAR();
                frontRight.SAR();
                backLeft.SAR();
                backRight.SAR();
                break;
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
        }
    }

    /**
     * Sets the mode of the motor to RUN_WITHOUT_ENCODERS using case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RWE(@NonNull DTMotors m) {
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.RWE();
                frontRight.RWE();
                backLeft.RWE();
                backRight.RWE();
                break;
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
        }
    }

    /**
     * Sets the mode of the motor to RUN_USING_ENCODERS using case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    @Override
    public void RUE(@NonNull DTMotors m) {
        switch (m) {
            case m:
                throw new IllegalArgumentException("Midshift is an illegal argument for TankDrive");
            case all:
            case dt:
                frontLeft.RUE();
                frontRight.RUE();
                backLeft.RUE();
                backRight.RUE();
                break;
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
        }
    }

    /**
     * Sets tolerance of motor
     *
     * @param i Tolerance in ticks to consider not busy when running to position
     */
    public void ST(int i) {
        frontLeft.ST(i);
        backLeft.ST(i);
        backRight.ST(i);
        frontRight.ST(i);
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
    public void drive(@NonNull DrivetrainHolonomic.Directions direction, double inches, double speed) {
        SAR(DTMotors.dt);
        RUE(DTMotors.dt);
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
            default:
                throw new IllegalArgumentException(direction + " is an invalid direction for TankDrive");
        }
    }
}