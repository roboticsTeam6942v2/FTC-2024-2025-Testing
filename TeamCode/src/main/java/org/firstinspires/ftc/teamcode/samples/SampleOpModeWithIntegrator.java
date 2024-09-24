package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.subsystems.Integrator;

@Autonomous
public class SampleOpModeWithIntegrator extends LinearOpMode {
    private BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    private Integrator integrator = new Integrator();
    private Thread integratorThread;
    private volatile boolean running = true; // Use volatile to ensure visibility across threads

    public void runOpMode() {
        // hardware map
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        // setup imu parameters
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(parameters);

        integratorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ElapsedTime timer = new ElapsedTime();
                double lastTime = 0;
                while (running) {
                    // get current data
                    Acceleration acceleration = imu.getLinearAcceleration();
                    Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    double xA = acceleration.xAccel;
                    double yA = acceleration.yAccel;
                    double heading = AngleUnit.DEGREES.normalizeDegrees(angles.firstAngle);

                    double currentTime = timer.seconds();
                    double timeDelta = currentTime - lastTime; // less taxing then a timer.reset();
                    lastTime = currentTime; // update state for loop

                    // update the integrator
                    integrator.update(xA, yA, heading, timeDelta);
                    timer.reset();

                    // sleep to control stress on the cpu, as well as accuracy
                    try {
                        Thread.sleep(20); // bigger to stress cpu less but will make result less accurate, vice versa with smaller values
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        integratorThread.start();

        waitForStart();

        while (opModeIsActive()) {
//            telemetry.addData("Velocity X: ", integrator.getVelocityX());
//            telemetry.addData("Velocity Y: ", integrator.getVelocityY());
            telemetry.addData("Position X: ", integrator.getPositionX());
            telemetry.addData("Position Y: ", integrator.getPositionY());
            telemetry.update();
        }
        // stop the integrator when opMode is no longer active
        running = false;
        try {
            integratorThread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
