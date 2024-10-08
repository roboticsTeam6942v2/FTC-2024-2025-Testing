package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;

public class Basic extends LinearOpMode {
    @Override
    public void runOpMode() {
        Mecanum drivetrain = new Mecanum(new Motor[]{new Motor("backLeft", hardwareMap, "reverse", telemetry), new Motor("backRight", hardwareMap, telemetry), new Motor("frontLeft", hardwareMap, "reverse", telemetry), new Motor("frontRight", hardwareMap, telemetry)}, telemetry);

        waitForStart();
        while (opModeIsActive()) {
            drivetrain.teleOpDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);
        }
    }
}