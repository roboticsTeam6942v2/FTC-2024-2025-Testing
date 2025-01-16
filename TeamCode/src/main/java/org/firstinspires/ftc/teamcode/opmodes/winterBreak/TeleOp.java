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
public class TeleOp extends LinearOpMode {

    DcMotorEx elbow, backLeft, backRight, frontLeft, frontRight, foot, liftRope, liftChain;
    Servo fingers, wrist;

    double elbowBooster = 400, speedReduction = 0.45, turnDamper = .75;
    int tolerance = 10; // increase until the shaking stops

    boolean previous2AButtonState = false, fingersOpen = false, lock = true;

    ElapsedTime timer = new ElapsedTime();
    Constants.RobotPositions currentPosition;

    @Override
    public void runOpMode() {

        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backLeft.setDirection(Constants.BLFRDrivetrainDirection);
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backRight.setDirection(Constants.BRFLDrivetrainDirection);
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontLeft.setDirection(Constants.BRFLDrivetrainDirection);
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontRight.setDirection(Constants.BLFRDrivetrainDirection);

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

        foot = hardwareMap.get(DcMotorEx.class, "foot");
        foot.setDirection(Constants.footDirection);
        foot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        foot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elbow = hardwareMap.get(DcMotorEx.class, "elbow");
        elbow.setDirection(Constants.elbowDirection);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setTargetPosition(0);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setPower(1);

        liftRope = hardwareMap.get(DcMotorEx.class, "liftRope");
        liftRope.setDirection(Constants.liftRopeDirection);
        liftRope.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRope.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRope.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRope.setTargetPosition(0);
        liftRope.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRope.setPower(1);

        liftChain = hardwareMap.get(DcMotorEx.class, "liftChain");
        liftChain.setDirection(Constants.liftRopeDirection);
        liftChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftChain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftChain.setTargetPosition(0);
        liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftChain.setPower(1);

        telemetry.addData("Initialized", "");
        telemetry.update();

        currentPosition = Constants.RobotPositions.INITIAL;

        waitForStart();
        int lastTargetPosition = 0;
        double rumble = 0;

        currentPosition = Constants.RobotPositions.GROUND;

        while (opModeIsActive()) {

            // test
            if (gamepad1.guide && gamepad2.guide) {
                rumble += .01;
                gamepad1.rumble(rumble, rumble, 100);
                gamepad2.rumble(rumble, rumble, 100);
            } else {
                rumble = 0;
            }

            // try to make lift work
            int position = (int) (liftChain.getCurrentPosition() + (100 * gamepad1.right_trigger) - (100 * gamepad1.left_trigger));

            position = Math.min(6000, position);

            if (Math.abs(position - lastTargetPosition) > tolerance) {
                liftRope.setTargetPosition((int) (position * 1.05));
                liftChain.setTargetPosition(Math.max(200, position));

                lastTargetPosition = position;
            }

            if (gamepad1.back) {
                liftChain.setTargetPosition(0);
                gamepad1.rumbleBlips(1);
                gamepad2.rumbleBlips(1);
            }

            liftRope.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            liftRope.setPower(1);
            liftChain.setPower(1);

            // control elbow
            elbow.setTargetPosition(elbow.getCurrentPosition() + (int) ((gamepad2.right_trigger * elbowBooster) - (gamepad2.left_trigger * elbowBooster)));
            if (elbow.getTargetPosition() < 0) {
                elbow.setTargetPosition(0);
            } else if (elbow.getTargetPosition() > Constants.elbowExtend) {
                elbow.setTargetPosition(Constants.elbowExtend);
            }
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // foot controls
            if (gamepad1.right_bumper) {
                foot.setPower(1);
            } else if (gamepad1.left_bumper) {
                foot.setPower(-1);
            } else {
                foot.setPower(0);
            }

            // control wrist
            if (gamepad2.dpad_up) {
                wrist.setPosition(Constants.wristPlace);
            }
            if (gamepad2.dpad_left) {
                wrist.setPosition(Constants.wristGrab);
            }
            if (gamepad2.dpad_down) {
                wrist.setPosition(Constants.wristFold);
            }
            telemetry.addData("wrist", wrist.getPosition());

            // control fingers
            boolean current2AButtonState = gamepad2.a;
            if (current2AButtonState && !previous2AButtonState) {
                fingersOpen = !fingersOpen;
            }
            telemetry.addData("finger: ", fingersOpen ? "open" : "closed");
            if (fingersOpen) {
                fingers.setPosition(Constants.fingersOpen);
            } else {
                fingers.setPosition(Constants.fingersClosed);
            }
            previous2AButtonState = current2AButtonState;

            // drive
            double
                    y = gamepad1.left_stick_y,
                    rx = gamepad1.right_stick_x,
                    x = -gamepad1.left_stick_x,
                    denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeft.setPower(((y + x * 1.1 + rx * turnDamper) / denominator) * speedReduction);
            backLeft.setPower(((y - x * 1.1 + rx * turnDamper) / denominator) * speedReduction);
            frontRight.setPower(((y - x * 1.1 - rx * turnDamper) / denominator) * speedReduction);
            backRight.setPower(((y + x * 1.1 - rx * turnDamper) / denominator) * speedReduction);

            telemetry.update();
        }
    }
}
