package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public class RamRight extends LinearOpMode {

    DcMotorEx elbow, backLeft, backRight, frontLeft, frontRight, foot, liftRope, liftChain;
    Servo fingers, wrist;

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
        liftChain.setDirection(Constants.liftChainDirection);
        liftChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftChain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftChain.setTargetPosition(0);
        liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftChain.setPower(1);

        telemetry.addData("Initialized", "");
        telemetry.update();

        waitForStart();

        // BR IS + FORWARD
        // FR IS + LEFT

        if (opModeIsActive()) {

            sleep(5000); // wait 5 seconds for fun

            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            while (backRight.getCurrentPosition() < 50000 && opModeIsActive()) {
                telemetry.addData("pos: ", 50000-backRight.getCurrentPosition()-frontRight.getCurrentPosition());
                telemetry.update();
                backLeft.setPower(.35);
                backRight.setPower(-.25);
                frontLeft.setPower(-.25);
                frontRight.setPower(.35);
            }

            backLeft.setPower(0);
            backRight.setPower(0);
            frontLeft.setPower(0);
            frontRight.setPower(0);

            while (opModeIsActive()) {
                idle();
            }
        }
    }
}
