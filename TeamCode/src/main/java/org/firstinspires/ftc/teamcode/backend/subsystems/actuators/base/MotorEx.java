package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

import java.util.HashMap;

public class MotorEx extends Motor {

    private final MotorConfigurationType motorConfiguration;
    private final static HashMap<MotorType, MotorConfigurationType> motorTypeMap = new HashMap<>();

    static {
        MotorConfigurationType torquenado = MotorConfigurationType.getMotorType(DcMotorEx.class);
        torquenado.setTicksPerRev(1440);
        torquenado.setMaxRPM(100);
        torquenado.setGearing(60);
        torquenado.setOrientation(null);
        torquenado.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.TORQUENADO, torquenado);
        MotorConfigurationType neverest40Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest40Gearmotor.setTicksPerRev(1120);
        neverest40Gearmotor.setMaxRPM(160);
        neverest40Gearmotor.setGearing(40);
        neverest40Gearmotor.setOrientation(null);
        neverest40Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_40_GEARMOTOR, neverest40Gearmotor);
        MotorConfigurationType neverest60Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest60Gearmotor.setTicksPerRev(1680);
        neverest60Gearmotor.setMaxRPM(105);
        neverest60Gearmotor.setGearing(60);
        neverest60Gearmotor.setOrientation(null);
        neverest60Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_60_GEARMOTOR, neverest60Gearmotor);
        MotorConfigurationType neverest3_7OrbitalGearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest3_7OrbitalGearmotor.setTicksPerRev(103.6);
        neverest3_7OrbitalGearmotor.setMaxRPM(1780);
        neverest3_7OrbitalGearmotor.setGearing(3.7);
        neverest3_7OrbitalGearmotor.setOrientation(null);
        neverest3_7OrbitalGearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_3_7_ORBITAL_GEARMOTOR, neverest3_7OrbitalGearmotor);
        MotorConfigurationType neverest20OrbitalGearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverest20OrbitalGearmotor.setTicksPerRev(537.6);
        neverest20OrbitalGearmotor.setMaxRPM(340);
        neverest20OrbitalGearmotor.setGearing(19.2);
        neverest20OrbitalGearmotor.setOrientation(null);
        neverest20OrbitalGearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_20_ORBITAL_GEARMOTOR, neverest20OrbitalGearmotor);
        MotorConfigurationType revHexCore = MotorConfigurationType.getMotorType(DcMotorEx.class);
        revHexCore.setTicksPerRev(288);
        revHexCore.setMaxRPM(125);
        revHexCore.setGearing(72);
        revHexCore.setOrientation(null);
        revHexCore.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.REV_HEX_CORE, revHexCore);
        MotorConfigurationType neverestHex1Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex1Gearmotor.setTicksPerRev(7);
        neverestHex1Gearmotor.setMaxRPM(6600);
        neverestHex1Gearmotor.setGearing(1);
        neverestHex1Gearmotor.setOrientation(null);
        neverestHex1Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_1_GEARMOTOR, neverestHex1Gearmotor);
        MotorConfigurationType neverestHex3_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex3_7Gearmotor.setTicksPerRev(25.9);
        neverestHex3_7Gearmotor.setMaxRPM(1784);
        neverestHex3_7Gearmotor.setGearing(3.7);
        neverestHex3_7Gearmotor.setOrientation(null);
        neverestHex3_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_3_7_GEARMOTOR, neverestHex3_7Gearmotor);
        MotorConfigurationType neverestHex13_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex13_7Gearmotor.setTicksPerRev(95.9);
        neverestHex13_7Gearmotor.setMaxRPM(482);
        neverestHex13_7Gearmotor.setGearing(13.7);
        neverestHex13_7Gearmotor.setOrientation(null);
        neverestHex13_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_13_7_GEARMOTOR, neverestHex13_7Gearmotor);
        MotorConfigurationType neverestHex19_2Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex19_2Gearmotor.setTicksPerRev(134.4);
        neverestHex19_2Gearmotor.setMaxRPM(344);
        neverestHex19_2Gearmotor.setGearing(19.2);
        neverestHex19_2Gearmotor.setOrientation(null);
        neverestHex19_2Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_19_2_GEARMOTOR, neverestHex19_2Gearmotor);
        MotorConfigurationType neverestHex50_9Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex50_9Gearmotor.setTicksPerRev(356.3);
        neverestHex50_9Gearmotor.setMaxRPM(130);
        neverestHex50_9Gearmotor.setGearing(50.9);
        neverestHex50_9Gearmotor.setOrientation(null);
        neverestHex50_9Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_50_9_GEARMOTOR, neverestHex50_9Gearmotor);
        MotorConfigurationType neverestHex263_7Gearmotor = MotorConfigurationType.getMotorType(DcMotorEx.class);
        neverestHex263_7Gearmotor.setTicksPerRev(1845.9);
        neverestHex263_7Gearmotor.setMaxRPM(25);
        neverestHex263_7Gearmotor.setGearing(263.7);
        neverestHex263_7Gearmotor.setOrientation(null);
        neverestHex263_7Gearmotor.setAchieveableMaxRPMFraction(1.0);
        motorTypeMap.put(MotorType.NEVEREST_HEX_263_7_GEARMOTOR, neverestHex263_7Gearmotor);
    }

    enum MotorType {
        TORQUENADO,
        NEVEREST_40_GEARMOTOR, NEVEREST_60_GEARMOTOR,
        NEVEREST_3_7_ORBITAL_GEARMOTOR, NEVEREST_20_ORBITAL_GEARMOTOR,
        REV_HEX_CORE,
        NEVEREST_HEX_1_GEARMOTOR, NEVEREST_HEX_3_7_GEARMOTOR, NEVEREST_HEX_13_7_GEARMOTOR, NEVEREST_HEX_19_2_GEARMOTOR, NEVEREST_HEX_50_9_GEARMOTOR, NEVEREST_HEX_263_7_GEARMOTOR
    }

    /**
     * Constructs a {@code Motor} object without specifying a direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public MotorEx(String name, HardwareMap hwMap, Telemetry telemetry, MotorType motorType) {
        this(name, hwMap, "", telemetry, motorType);
    }

    /**
     * Constructs a {@code Motor} object with a specified direction
     *
     * @param name      The name of the motor as configured in the robot configuration on the phone
     * @param hwMap     The {@link HardwareMap} object passed from the OpMode to map the motor
     * @param direction The direction of the motor ('f' for forward, 'r' for reverse)
     * @param telemetry The {@link Telemetry} object to display runtime information
     */
    public MotorEx(String name, HardwareMap hwMap, String direction, Telemetry telemetry, MotorType motorType) {
        super(name, hwMap, direction, telemetry);
        this.motorConfiguration = motorTypeMap.get(motorType);
    }
}
