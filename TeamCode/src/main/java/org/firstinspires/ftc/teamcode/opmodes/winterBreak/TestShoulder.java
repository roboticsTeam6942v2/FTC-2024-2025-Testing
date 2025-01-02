package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class TestShoulder extends LinearOpMode {
    Servo shoulderLeft, shoulderRight;

    double position = 0;
    Gamepad last = new Gamepad();
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        shoulderLeft = hardwareMap.get(Servo.class, "shoulderLeft");
        shoulderLeft.setPosition(position);
        shoulderRight = hardwareMap.get(Servo.class, "shoulderRight");
        shoulderRight.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE);
        shoulderRight.setPosition(position);

        waitForStart();
        timer.reset();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !last.dpad_up) {
                position+=.1;
            }
            if (gamepad1.dpad_down && !last.dpad_down) {
                position-=.1;
            }

            if (position > 1) {
                position = 1;
            } else if (position < 0) {
                position = 0;
            }


            shoulderLeft.setPosition(position);
            shoulderRight.setPosition(position);
            last.copy(gamepad1);
            telemetry.addData("time: ", timer.seconds());
            telemetry.addData("position: ", position);
            telemetry.update();
        }
    }
}
