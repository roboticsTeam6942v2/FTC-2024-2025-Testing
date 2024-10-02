package org.firstinspires.ftc.teamcode.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;
import org.firstinspires.ftc.teamcode.backend.subsystems.sensors.RGBSensor;

/**
 * TeleOp code to test sensors, will be updated as more sensors get added, uses mecanum drive
 */
@TeleOp
public class SensorTest extends LinearOpMode {
    public static Motor frontLeft, frontRight, backLeft, backRight;
    public static RGBSensor color;

    public void runOpMode(){
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, "reverse");
        frontRight = new Motor("frontRight", hardwareMap);
        backLeft = new Motor("backLeft", hardwareMap, "reverse");
        backRight = new Motor("backRight", hardwareMap);
        color = new RGBSensor("color", hardwareMap);

        // accessing motors in drivetrain any other way but through the mecanum class after this line will lead to an error
        Mecanum drivetrain = new Mecanum(new Motor[]{frontLeft, frontRight, backLeft, backRight});
        while (opModeIsActive()){
            // can change these if needed
            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            drivetrain.teleOpDrive(drive, turn, strafe);

            telemetry.addData("Red: ", color.getRed());
            telemetry.addData("Blue: ", color.getBlue());
            telemetry.addData("Green: ", color.getGreen());
            telemetry.update();

            if(color.redBoundary(100, 300)){
                telemetry.addData("Red Boundary","");
                telemetry.update();
            } else if (color.blueBoundary(100, 300)){
                telemetry.addData("Blue Boundary","");
                telemetry.update();
            } else if (color.greenBoundary(100, 300)){
                telemetry.addData("Green Boundary","");
                telemetry.update();
            } else {
                telemetry.addData("No Boundary","");
                telemetry.update();
            }
        }
    }
}