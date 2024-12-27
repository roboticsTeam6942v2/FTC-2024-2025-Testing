package org.firstinspires.ftc.teamcode.opmodes.remoteDirections_deprecated_;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Servo;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.drivetrains.Mecanum;

import java.util.concurrent.TimeUnit;

@TeleOp
@Disabled
public class TelepV2 extends LinearOpMode {

    Motor shoulderMotorLeft, shoulderMotorRight, elbowMotor, backLeft, backRight, frontLeft, frontRight;
    Servo fingers, wrist;

    ElapsedTime timer = new ElapsedTime();
    Mecanum drivetrain;
    Gamepad last = new Gamepad();
    Constants.RobotPositions currentPosition;

    @Override
    public void runOpMode() {
        backLeft = new Motor("backLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        backRight = new Motor("backRight", hardwareMap, telemetry);
        frontLeft = new Motor("frontLeft", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        frontRight = new Motor("frontRight", hardwareMap, telemetry);

        backLeft.runWithoutEncoder();
        backRight.runWithoutEncoder();
        frontLeft.runWithoutEncoder();
        frontRight.runWithoutEncoder();

        drivetrain = new Mecanum(new Motor[]{backLeft, backRight, frontLeft, frontRight}, telemetry);

        shoulderMotorLeft = new Motor("shoulderMotorLeft", hardwareMap, telemetry);
        shoulderMotorRight = new Motor("shoulderMotorRight", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        elbowMotor = new Motor("elbowMotor", hardwareMap, DcMotorSimple.Direction.REVERSE, telemetry);
        fingers = new Servo("fingers", hardwareMap, telemetry);

        fingers.setPosition(Constants.fingersGrab);
        wrist.setPosition(Constants.wristInitial);

        shoulderMotorRight.setTargetPosition(0);
        shoulderMotorLeft.setTargetPosition(0);
        elbowMotor.setTargetPosition(0);

        shoulderMotorLeft.runToPosition();
        shoulderMotorRight.runToPosition();
        elbowMotor.runToPosition();

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

        shoulderMotorLeft.runToPosition();
        shoulderMotorRight.runToPosition();
        elbowMotor.runToPosition();

        shoulderMotorLeft.setPower(1);
        shoulderMotorRight.setPower(1);
        elbowMotor.setPower(1);

        sleep(20);
        wrist.setPosition(Constants.wristGrab);
        fingers.setPosition(Constants.fingersGrab);

        while (opModeIsActive()) {
            drivetrain.teleOpDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

            if (gamepad1.dpad_up && !last.dpad_up) {
                if (currentPosition == Constants.RobotPositions.GRABBING) {
                    currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HIGH_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristPlace);
                }
            }

            if (gamepad1.dpad_down && !last.dpad_down) {
                if (currentPosition == Constants.RobotPositions.HIGH_BASKET) {
                    currentPosition = Constants.RobotPositions.LOW_BASKET;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristPlace);
                } else if (currentPosition == Constants.RobotPositions.LOW_BASKET) {
                    currentPosition = Constants.RobotPositions.HOVERING_TO_GRAB;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristGrab);
                } else if (currentPosition == Constants.RobotPositions.HOVERING_TO_GRAB) {
                    currentPosition = Constants.RobotPositions.GRABBING;

                    shoulderMotorRight.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    shoulderMotorLeft.setTargetPosition(Constants.positions.get(currentPosition).shoulder);
                    elbowMotor.setTargetPosition(Constants.positions.get(currentPosition).elbow);

                    shoulderMotorLeft.runToPosition();
                    shoulderMotorRight.runToPosition();
                    elbowMotor.runToPosition();

                    wrist.setPosition(Constants.wristGrab);
                }
            }

            if (gamepad1.a && !last.a && timer.now(TimeUnit.SECONDS) < .5) {
                fingers.setPosition(Constants.fingersEject);
                timer.reset();
            } else if (timer.now(TimeUnit.SECONDS) > .5) {
                fingers.setPosition(Constants.fingersGrab);
            }

            last.copy(gamepad1);

            telemetry.addData("current position: ", currentPosition.toString());
            telemetry.update();
        }
    }
}
