package org.firstinspires.ftc.teamcode.backend.subsystems.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;

public class UpdatedIMU extends subsystem {
    private Orientation globalAngles = new Orientation();
    private double referenceAngle;
    private BNO055IMU imu;
    private BNO055IMU.Parameters parameters;
    private Telemetry telemetry;

    public UpdatedIMU(String name, @NonNull HardwareMap hwMap, Telemetry telemetry) {
        this(name, hwMap, null, telemetry);
    }
    public UpdatedIMU(String name, @NonNull HardwareMap hwMap, BNO055IMU.Parameters parameters, Telemetry telemetry) {
        imu = hwMap.get(BNO055IMU.class, name);
        if (parameters!=null) {
            this.parameters = parameters;
        } else {
            this.parameters = new BNO055IMU.Parameters();
            this.parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            this.parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            this.parameters.loggingEnabled = false;
//        this.parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imu.initialize(this.parameters);
        }
        referenceAngle = 0;
        this.telemetry = telemetry;
    }

    /**
     * Resets the global angle of the IMU
     */
    public void resetAngle() {
        globalAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        referenceAngle = 0;
    }
    /**
     * Returns the current angle of the robot
     * @return referenceAngle Current angle
     */
    public double getAngle() {
        return this.getAngle(AngleUnit.DEGREES);
    }
    /**
     * Returns the current angle of the robot
     * @param angleunit Unit you want angle to be in
     * @return referenceAngle Current angle
     */
    public double getAngle(AngleUnit angleunit) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleunit);
        referenceAngle += AngleUnit.DEGREES.normalizeDegrees(angles.firstAngle);
        globalAngles = angles;
        return referenceAngle;
    }
    /**
     * Returns the global angle of the robot
     * @return globalAngle the global angle
     */
    public double getGlobalAngle(AngleUnit angleunit) {
        return globalAngles.firstAngle;
    }
    public Acceleration getAcceleration () {
        return imu.getLinearAcceleration();
    }
}
