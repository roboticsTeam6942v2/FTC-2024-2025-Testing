package org.firstinspires.ftc.teamcode.opmodes.remoteDirections_deprecated_;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;

@TeleOp
@Disabled
public class FindLimitsV2 extends LinearOpMode {

    Motor shoulderMotorLeft, shoulderMotorRight, elbowMotor;
    Servo fingers, wrist;

    Gamepad last = new Gamepad();
    double fingerPosition = 0, wristPosition = 0;
    int deltaTargetShoulderPosition;

    @Override
    public void runOpMode() {
        shoulderMotorLeft = new Motor("shoulderMotorLeft", hardwareMap, telemetry);
        shoulderMotorRight = new Motor("shoulderMotorRight", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        elbowMotor = new Motor("elbowMotor", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        fingers = new Servo("fingers", hardwareMap, telemetry);

        fingers.setPosition(fingerPosition);
        wrist.setPosition(wristPosition);

        shoulderMotorRight.setTargetPosition(0);
        shoulderMotorLeft.setTargetPosition(0);
        elbowMotor.setTargetPosition(0);

        shoulderMotorLeft.runToPosition();
        shoulderMotorRight.runToPosition();
        elbowMotor.runToPosition();

        shoulderMotorLeft.setPower(1);
        shoulderMotorRight.setPower(1);
        elbowMotor.setPower(1);

        waitForStart();

        if (Constants.elbowOpen != null && Constants.shoulderOpen != null && Constants.wristOpen != null) {
            shoulderMotorLeft.setTargetPosition(Constants.shoulderOpen);
            shoulderMotorRight.setTargetPosition(Constants.shoulderOpen);
            elbowMotor.setTargetPosition(Constants.elbowOpen);

            shoulderMotorLeft.runToPosition();
            shoulderMotorRight.runToPosition();
            elbowMotor.runToPosition();

            sleep(20);
            wrist.setPosition(Constants.wristOpen);
        }

        while (opModeIsActive()) {
            if (!shoulderMotorLeft.isBusy() && !shoulderMotorRight.isBusy()) {
//                deltaTargetPosition = (int) ((Constants.SHOULDER_BOOSTER * gamepad1.right_trigger) - (Constants.SHOULDER_BOOSTER * gamepad1.left_trigger));
                deltaTargetShoulderPosition = (int) (Constants.SHOULDER_BOOSTER * -gamepad1.left_stick_y);
                shoulderMotorLeft.setTargetPosition(shoulderMotorLeft.getCurrentPosition() + deltaTargetShoulderPosition);
                shoulderMotorRight.setTargetPosition(shoulderMotorRight.getCurrentPosition() + deltaTargetShoulderPosition);
            }

            if (!elbowMotor.isBusy())
                elbowMotor.setTargetPosition(elbowMotor.getCurrentPosition() + (int) (Constants.ELBOW_BOOSTER * -gamepad1.right_stick_y));

            if (gamepad1.dpad_up && last.dpad_up)
                fingerPosition += Constants.FINGER_STEP;

            if (gamepad1.dpad_down && last.dpad_down)
                fingerPosition -= Constants.FINGER_STEP;

            if (gamepad1.y && last.y)
                wristPosition += Constants.WRIST_STEP;

            if (gamepad1.a && last.a)
                wristPosition -= Constants.WRIST_STEP;

            telemetry.addData("shoulder: ", (shoulderMotorLeft.getCurrentPosition() + shoulderMotorRight.getCurrentPosition()) / 2);
            telemetry.addData("elbow: ", elbowMotor.getCurrentPosition());
            telemetry.addData("finger: ", fingerPosition);
            telemetry.addData("wrist: ", wristPosition);
            telemetry.update();

            fingers.setPosition(fingerPosition);
            wrist.setPosition(wristPosition);

            shoulderMotorLeft.runToPosition();
            shoulderMotorRight.runToPosition();
            elbowMotor.runToPosition();

            last.copy(gamepad1);
        }
    }
}
