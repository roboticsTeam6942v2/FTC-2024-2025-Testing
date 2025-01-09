package org.firstinspires.ftc.teamcode.opmodes.winterBreak.servoTests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opmodes.winterBreak.Constants;

@TeleOp
@Disabled
public class TestFingers extends LinearOpMode {
    Servo fingers;

    double position = 1;
    Gamepad last = new Gamepad();
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        fingers = hardwareMap.get(Servo.class, "fingers");
        fingers.setPosition(position);

        waitForStart();
        timer.reset();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && !last.dpad_up) {
//                position+=.05;
                position = Constants.fingersOpen;
            }
            if (gamepad1.dpad_down && !last.dpad_down) {
//                position-=.05;
                position = Constants.fingersClosed;
            }

            if (position > 1) {
                position = 1;
            } else if (position < 0) {
                position = 0;
            }


            fingers.setPosition(position);
            last.copy(gamepad1);
            telemetry.addData("time: ", timer.seconds());
            telemetry.addData("position: ", position);
            telemetry.update();
        }
    }
}
