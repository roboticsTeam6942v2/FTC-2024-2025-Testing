package org.firstinspires.ftc.teamcode.backend.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;

@Disabled
@Autonomous
public class AutonomousBasic extends LinearOpMode {
    public static Motor frontLeft, frontRight, backLeft, backRight;

    public void runOpMode() {
        // declare motors
        frontLeft = new Motor("frontLeft", hardwareMap, "reverse");
        frontRight = new Motor("frontRight", hardwareMap);
        backLeft = new Motor("backLeft", hardwareMap, "reverse");
        backRight = new Motor("backRight", hardwareMap);

        // accessing motors in drivetrain any other way but through the mecanum class after this line will lead to an error
        Mecanum drivetrain = new Mecanum(new Motor[]{frontLeft, frontRight, backLeft, backRight});
        while (opModeIsActive()) {
            // drive forward 12 inches at a speed of 1 (full speed)
            drivetrain.drive("f", 12, 1);
        }
    }
}
