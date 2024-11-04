package org.firstinspires.ftc.teamcode.opmodes.remoteDirections;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@TeleOp
public class Teleop extends LinearOpMode {

    DcMotorEx shoulderMotorLeft, shoulderMotorRight, elbowMotor, backLeft, backRight, frontLeft, frontRight;
    Servo fingers, wrist;

    ElapsedTime timer = new ElapsedTime();
    Gamepad last = new Gamepad();
    Constants.RobotPositions currentPosition;
    double speedReduction = 1; // 1 is no reduction, the closer to 0 the slower

    @Override
    public void runOpMode() {

        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fingers = hardwareMap.get(Servo.class, "fingers");
        wrist = hardwareMap.get(Servo.class, "wrist");

        fingers.setPosition(Constants.fingersGrab);
        wrist.setPosition(Constants.wristInitial);

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

        telemetry.addData("Initialized", "");
        telemetry.update();

        waitForStart();

        currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

        shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
        shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
        elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

        shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shoulderMotorLeft.setPower(1);
        shoulderMotorRight.setPower(1);
        elbowMotor.setPower(1);

        sleep(20);
        wrist.setPosition(Constants.wristGrab);
        fingers.setPosition(Constants.fingersGrab);

        while (opModeIsActive()) {

            double
                    y = -gamepad1.left_stick_y,
                    rx = gamepad1.right_stick_x,
                    x = gamepad1.left_stick_x,
                    denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            frontLeft.setPower(((y + x * 1.1 + rx) / denominator) * speedReduction);
            backLeft.setPower(((y - x * 1.1 + rx) / denominator) * speedReduction);
            frontRight.setPower(((y - x * 1.1 - rx) / denominator) * speedReduction);
            backRight.setPower(((y + x * 1.1 - rx) / denominator) * speedReduction);

            if (gamepad1.dpad_up && !last.dpad_up) {
                if (currentPosition == Constants.RobotPositions.GRABBING) {
                    currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HIGH_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                }
            }

            if (gamepad1.dpad_down && !last.dpad_down) {
                if (currentPosition == Constants.RobotPositions.HIGH_BASKET) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.GRABBING;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shoulderMotorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                }
            }

            if (gamepad1.a && !last.a && timer.now(TimeUnit.SECONDS) < .5) {
                fingers.setPosition(Constants.fingersEject);
                timer.reset();
            } else if (timer.now(TimeUnit.SECONDS) > .5) {
                fingers.setPosition(Constants.fingersGrab);
            }

            switch (currentPosition) {
                case INITIAL:
                    speedReduction = 1;
                    break;
                case GRABBING:
                case HOVERING_TO_GRAB:
                    speedReduction = 0.4;
                    break;
                case LOW_BASKET:
                case HIGH_BASKET:
                    speedReduction = .8;
                    break;
            }

            last.copy(gamepad1);

            telemetry.addData("current position: ", currentPosition.toString());
            telemetry.update();
        }
    }
}
