package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.core.LynxGetMotorEncoderPositionCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorChannelModeCommand;
import com.qualcomm.hardware.lynx.commands.core.LynxSetMotorTargetPositionCommand;
import com.qualcomm.robotcore.exception.TargetPositionNotSetException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

/**
 * Motor Object, used to declare the motors within the programming of the robot
 */
public class Motor extends subsystem implements Comparable<Motor> {
    private DcMotorEx motor;
    private String name;
    private Integer globalTicks, tolerance, targetPosition;
    private final LynxModule lynxModule;
    private final int motorPort;

    /**
     * Sets the variables for the motor Object without direction
     *
     * @param name  Name of the motor in the phone
     * @param hwMap HardwareMap object from OpMode
     */
    public Motor(String name, HardwareMap hwMap, Telemetry telemetry) {
        this(name, hwMap, "", telemetry);
    }

    /**
     * Sets the variables for the motor Object
     *
     * @param name      Name of the motor in the code
     * @param hwMap     Name of the motor within the phones
     * @param direction Direction of the motor (f or r)
     */
    public Motor(String name, HardwareMap hwMap, String direction, Telemetry telemetry) {
        // after this initialization setup, we are going to go as base level as possible to optimize
        super(hwMap, telemetry);
        motor = hwMap.get(DcMotorEx.class, name);
        motor.setDirection(direction.toLowerCase().charAt(0) == 'r' ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.name = name;
        lynxModule = (LynxModule) motor.getController();
        motorPort = motor.getPortNumber();
        tolerance = 10;
        targetPosition = null;
//        MotorConfigurationType
    }

    /**
     * Returns the name of the motor Object
     *
     * @return name Name of the motor
     */
    public String getName() {
        return name;
    } // made so we can sort if needed

    /**
     * Sets the power of the specific motor Object, Ex frontLeft.SP(1);
     *
     * @param power Power of the motor, between -1 and 1
     */
    public void SP(double power) {
        motor.setPower(power);
//        while (true) {
//            try {
//                new LynxSetMotorPowerCommand(lynxModule, motorPort, power);
//            } catch (Exception e) {
//
//            }
        }

    /**
     * Sets a target position for the encoders within the motor Object, Ex frontLeft.STP(100);
     *
     * @param targetPosition Target Position (in ticks)
     */
    public void STP(int targetPosition) {
        this.targetPosition = targetPosition + globalTicks;
        while (true) {
            try {
                new LynxSetMotorTargetPositionCommand(lynxModule, motorPort, this.targetPosition, tolerance).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the mode of the motor Object to RUN_TO_POSITION, Ex frontLeft.RTP();
     */
    public void RTP() throws TargetPositionNotSetException {
        if (this.targetPosition == null)
            throw new TargetPositionNotSetException();
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the mode of the motor Object to STOP_AND_RESET_ENCODERS, Ex frontLeft.SAR();
     */
    public void SAR() {
        globalTicks = GCP();
    }

    /**
     * Sets the mode of the motor Object to RUN_WITHOUT_ENCODER, Ex frontLeft.RWE();
     */
    public void RWE() {
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE).send();
            } catch (Exception e) {

            }
        }
    }

    /**
     * Sets the mode of motor Object to RUN_USING_ENCODER, Ex frontLeft.RUE();
     */
    public void RUE() {
        while (true) {
            try {
                new LynxSetMotorChannelModeCommand(lynxModule, motorPort, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);
            } catch (Exception e) {

            }
        }
    }

    /**
     * Set the tolerance of the motor, used to determine how close the target position must be to the current position for isBusy() to return false
     *
     * @param ticks Number of ticks
     */
    public void ST(int ticks) {
        this.tolerance = ticks;
    }

    /**
     * Returns whether or not a motor is busy, Ex frontLeft.isBusy();
     *
     * @return isBusy (true or false)
     */
    public boolean isBusy() {
        return Math.abs(GCP() - GTP()) > this.tolerance;
    }

    /**
     * Returns the current position of the motor Object, Ex frontLeft.GCP();
     *
     * @return The current position of the motor in ticks
     */
    public int GCP() {
        while (true) {
            try {
                return new LynxGetMotorEncoderPositionCommand(lynxModule, motorPort).sendReceive().getPosition() - globalTicks;
            } catch (Exception e) {

            }
        }
    }

    /**
     * Returns the target position of the motor Object, Ex frontLeft.GTP();
     *
     * @return The target position of the motor in ticks
     */
    public int GTP() {
        return this.targetPosition;
    }

    /**
     * Returns the power of the motor Object, Ex frontLeft.GP();
     *
     * @return The motor's power as a double
     */
    public double GP() {
        return motor.getPower();
    }

    /**
     * Compares this motor to another motor by name
     *
     * @param otherMotor Motor object
     * @return Alphabetical order sorting math
     */
    @Override
    public int compareTo(Motor otherMotor) {
        return this.name.compareTo(otherMotor.getName());
    }
}
