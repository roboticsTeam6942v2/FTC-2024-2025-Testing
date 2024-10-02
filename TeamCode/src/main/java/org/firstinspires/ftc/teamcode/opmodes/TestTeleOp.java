package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.teamcode.backend.subsystems.Integrator;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.UpdatedIMU;

@TeleOp
public class TestTeleOp extends LinearOpMode {
    UpdatedIMU imu;
    private Integrator integrator = new Integrator();
    private Thread integratorThread;
    private volatile boolean running = true;

    @Override
    public void runOpMode() {
        Mecanum drivetrain = new Mecanum(new Motor[]{new Motor("backLeft", hardwareMap, "reverse"), new Motor("backRight", hardwareMap), new Motor("frontLeft", hardwareMap, "reverse"), new Motor("frontRight", hardwareMap)});

        imu = new UpdatedIMU("imu", hardwareMap);

        integratorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ElapsedTime timer = new ElapsedTime();
                double lastTime = 0;
                while (running) {
                    // get current data
                    Acceleration acceleration = imu.getAcceleration();
                    double xA = acceleration.xAccel;
                    double yA = acceleration.yAccel;
                    double heading = imu.getAngle();

                    double currentTime = timer.seconds();
                    double timeDelta = currentTime - lastTime;
                    lastTime = currentTime;

                    integrator.update(xA, yA, heading, timeDelta);
                    timer.reset();

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        integratorThread.start();

        waitForStart();

        while (opModeIsActive()) {
            drivetrain.teleOpDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

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
