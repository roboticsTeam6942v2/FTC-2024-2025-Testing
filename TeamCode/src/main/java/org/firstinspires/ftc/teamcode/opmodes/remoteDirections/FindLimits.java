package org.firstinspires.ftc.teamcode.opmodes.remoteDirections;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FindLimits extends LinearOpMode {

    DcMotorEx shoulderMotorLeft, shoulderMotorRight, elbowMotor;
    Servo fingers, wrist;

    Gamepad last = new Gamepad();
    double fingerPosition = 0,  wristPosition = 0;
    int deltaTargetShoulderPosition;

    @Override
    public void runOpMode() {

        fingers = hardwareMap.get(Servo.class, "fingers");
        fingers.setPosition(fingerPosition);
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist.setPosition(wristPosition);

        shoulderMotorLeft = hardwareMap.get(DcMotorEx.class, "shoulderMotorLeft");
        shoulderMotorLeft.setDirection(Constants.shoulderMotorLeftDirection);
        shoulderMotorRight = hardwareMap.get(DcMotorEx.class, "shoulderMotorRight");
        shoulderMotorRight.setDirection(Constants.shoulderMotorRightDirection);
        elbowMotor = hardwareMap.get(DcMotorEx.class, "elbowMotor");
        elbowMotor.setDirection(Constants.elbowMotorDirection);

        shoulderMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulderMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shoulderMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shoulderMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulderMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shoulderMotorLeft.setTargetPosition(0);
        shoulderMotorRight.setTargetPosition(0);
        elbowMotor.setTargetPosition(0);

        shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shoulderMotorLeft.setPower(1);
        shoulderMotorRight.setPower(1);
        elbowMotor.setPower(1);

        waitForStart();

        if (Constants.elbowOpen != null && Constants.shoulderOpen != null && Constants.wristOpen != null) {
            shoulderMotorLeft.setTargetPosition(Constants.shoulderOpen);
            shoulderMotorRight.setTargetPosition(Constants.shoulderOpen);
            elbowMotor.setTargetPosition(Constants.elbowOpen);

            shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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

            shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            last.copy(gamepad1);
        }
    }
}
