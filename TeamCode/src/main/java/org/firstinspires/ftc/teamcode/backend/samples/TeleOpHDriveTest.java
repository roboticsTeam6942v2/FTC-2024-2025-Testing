package org.firstinspires.ftc.teamcode.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.HDrive;

/**
 * TeleOp code to test hdrive
 */
@Disabled
@TeleOp
public class TeleOpHDriveTest extends LinearOpMode {
    public static Motor frontLeft, frontRight, backLeft, backRight, midshift;

    public void runOpMode() {
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, "reverse", telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);
        backLeft = new Motor("backLeft", hardwareMap, "reverse", telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);
        midshift = new Motor("midshift", hardwareMap, telemetry);

        HDrive robot = new HDrive(new Motor[]{frontLeft, frontRight, backLeft, backRight}, midshift, telemetry);
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            robot.teleOpDrive(drive, turn, strafe);

            /**
             * speed reduction
             */
            if(gamepad1.start){
                robot.teleOpDrive(drive, turn, strafe, 3);
            }
        }
    }
}