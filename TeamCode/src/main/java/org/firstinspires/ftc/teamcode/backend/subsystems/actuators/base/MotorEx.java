package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorEx extends Motor {

    private final MotorConfigurationType motorConfiguration;

    /**
     * Constructs a {@code Motor} object without specifying a direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public MotorEx(String name, HardwareMap hwMap, Telemetry telemetry) {
        this(name, hwMap, "", telemetry);
    }

    /**
     * Constructs a {@code Motor} object with a specified direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param direction The direction of the motor ('f' for forward, 'r' for reverse)
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public MotorEx(String name, HardwareMap hwMap, String direction, Telemetry telemetry) {
        super(name, hwMap, direction, telemetry);
        this.motorConfiguration = super.getMotorConfiguration();
    }
}
