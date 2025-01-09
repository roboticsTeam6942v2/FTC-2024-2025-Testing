package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
@Disabled
public class TeleOp extends LinearOpMode {

    DcMotorEx elbowMotor, backLeft, backRight, frontLeft, frontRight;
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

        fingers.setPosition(Constants.fingersClosed);
        wrist.setPosition(Constants.wristFold);

        elbowMotor = hardwareMap.get(DcMotorEx.class, "elbowMotor");
        elbowMotor.setDirection(Constants.elbowDirection);

        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elbowMotor.setTargetPosition(0);

        elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elbowMotor.setPower(1);

        telemetry.addData("Initialized", "");
        telemetry.update();

        waitForStart();

        currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;
        elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

        elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elbowMotor.setPower(1);

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

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HIGH_BASKET;

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                }
            }

            if (gamepad1.dpad_down && !last.dpad_down) {
                if (currentPosition == Constants.RobotPositions.HIGH_BASKET) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.GRABBING;

                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    wrist.setPosition(Constants.wristGrab);
                }
            }

            if (gamepad1.a && !last.a) {
                fingers.setPosition(Constants.fingersOpen);
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
