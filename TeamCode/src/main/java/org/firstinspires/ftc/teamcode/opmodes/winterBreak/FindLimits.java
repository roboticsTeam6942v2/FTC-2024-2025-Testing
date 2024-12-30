package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Function;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class FindLimits extends LinearOpMode {

    DcMotorEx foot, liftRope, liftChain, elbow;
    Servo fingers, wrist, shoulder;

    int position = 1;

    String
            spacer = "   ",
            selector = " > ";

    Boolean
            footFrozen,
            liftFrozen,
            elbowFrozen;
    Function <Boolean, String> frozenCheck = (isFrozen) -> isFrozen ? "frozen" : "unfrozen";

    Gamepad last = new Gamepad();
    double fingerPosition = 0, wristPosition = 0, shoulderPosition = 0;

    Mode currentMode = Mode.DIRECTIONAL;

    enum Mode {
        DIRECTIONAL, MANUAL, FREEZE_MOTOR
    }

    @Override
    public void runOpMode() {

        // basic prepwork
        fingers = hardwareMap.get(Servo.class, "fingers");
        fingers.setPosition(fingerPosition);
        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist.setPosition(wristPosition);
        shoulder = hardwareMap.get(Servo.class, "shoulder");
        shoulder.setPosition(shoulderPosition);
        foot = hardwareMap.get(DcMotorEx.class, "foot");
        foot.setDirection(Constants.footDirection);
        liftRope = hardwareMap.get(DcMotorEx.class, "liftRope");
        liftRope.setDirection(Constants.liftRopeDirection);
        liftChain = hardwareMap.get(DcMotorEx.class, "liftChain");
        liftChain.setDirection(Constants.liftChainDirection);
        elbow = hardwareMap.get(DcMotorEx.class, "elbow");
        elbow.setDirection(Constants.elbowDirection);

        // if we get the numbers then open up
        if (!Constants.directionIsSetUp) {
            setModeDirectional();
        } else {
            setModeManual();
            foot.setTargetPosition(0);
            liftRope.setTargetPosition(Constants.liftHover);
            liftChain.setTargetPosition((int) Math.round(Constants.liftHover * 1.01));
            elbow.setTargetPosition(0);
            foot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftRope.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            foot.setPower(1);
            liftRope.setPower(1);
            liftChain.setPower(1);
            elbow.setPower(1);
        }

        while (opModeIsActive()) {

//------------------------------------------------------------------- Manual
            if (currentMode == Mode.DIRECTIONAL) {

                // make telemetry and menu
                if (position == 1) {
                    telemetry.addData(selector + "foot: ", Constants.footDirection);
                    telemetry.addData(spacer + "liftRope: ", Constants.liftRopeDirection);
                    telemetry.addData(spacer + "liftChain: ", Constants.liftChainDirection);
                    telemetry.addData(spacer + "elbow: ", Constants.elbowDirection);
                } else if (position == 2) {
                    telemetry.addData(spacer + "foot: ", Constants.footDirection);
                    telemetry.addData(selector + "liftRope: ", Constants.liftRopeDirection);
                    telemetry.addData(spacer + "liftChain: ", Constants.liftChainDirection);
                    telemetry.addData(spacer + "elbow: ", Constants.elbowDirection);
                } else if (position == 3) {
                    telemetry.addData(spacer + "foot: ", Constants.footDirection);
                    telemetry.addData(spacer + "liftRope: ", Constants.liftRopeDirection);
                    telemetry.addData(selector + "liftChain: ", Constants.liftChainDirection);
                    telemetry.addData(spacer + "elbow: ", Constants.elbowDirection);
                } else if (position == 4) {
                    telemetry.addData(spacer + "foot: ", Constants.footDirection);
                    telemetry.addData(spacer + "liftRope: ", Constants.liftRopeDirection);
                    telemetry.addData(spacer + "liftChain: ", Constants.liftChainDirection);
                    telemetry.addData(selector + "elbow: ", Constants.elbowDirection);
                }

                // activate motors
                if (gamepad1.a) {
                    if (position == 1) {
                        foot.setPower(.5);
                    } else {
                        foot.setPower(0);
                    }
                    if (position == 2) {
                        liftRope.setPower(.5);
                    } else {
                        liftRope.setPower(0);
                    }
                    if (position == 3) {
                        liftChain.setPower(.5);
                    } else {
                        liftChain.setPower(0);
                    }
                    if (position == 4) {
                        elbow.setPower(.5);
                    } else {
                        elbow.setPower(0);
                    }
                }

                // control motor selection
                if (!last.dpad_up && gamepad1.dpad_up) {
                    position++;
                    if (position > 4) {
                        position = 1;
                    }
                }
                if (!last.dpad_down && gamepad1.dpad_down) {
                    position++;
                    if (position < 1) {
                        position = 4;

                    }
                }
            }

//------------------------------------------------------------------- Manual
            if (currentMode == Mode.MANUAL) {

                // make telemetry and menu
                if (position == 1) {
                    telemetry.addData(selector + "foot: ", foot.getCurrentPosition());
                    telemetry.addData(spacer + "lift: ", liftRope.getCurrentPosition());
                    telemetry.addData(spacer + "elbow: ", elbow.getCurrentPosition());
                } else if (position == 2) {
                    telemetry.addData(spacer + "foot: ", foot.getCurrentPosition());
                    telemetry.addData(selector + "lift: ", liftRope.getCurrentPosition());
                    telemetry.addData(spacer + "elbow: ", elbow.getCurrentPosition());
                } else if (position == 3) {
                    telemetry.addData(spacer + "foot: ", foot.getCurrentPosition());
                    telemetry.addData(spacer + "lift: ", liftRope.getCurrentPosition());
                    telemetry.addData(selector + "elbow: ", elbow.getCurrentPosition());
                }

                // cycle modes
                if (!last.back && gamepad1.back) {
                    setModeFreezeMotor();
                    break;
                }

                // control motor selection
                if (!last.dpad_up && gamepad1.dpad_up) {
                    position++;
                    if (position > 3) {
                        position = 1;
                    }
                }
                if (!last.dpad_down && gamepad1.dpad_down) {
                    position++;
                    if (position < 1) {
                        position = 3;
                    }
                }

            }

//------------------------------------------------------------------- Freeze
            if (currentMode == Mode.FREEZE_MOTOR) {

                // make telemetry and menu
                if (position == 1) {
                    telemetry.addData(selector + "foot: ", frozenCheck.apply(footFrozen));
                    telemetry.addData(spacer + "lift: ", frozenCheck.apply(liftFrozen));
                    telemetry.addData(spacer + "elbow: ", frozenCheck.apply(elbowFrozen));
                } else if (position == 2) {
                    telemetry.addData(spacer + "foot: ", frozenCheck.apply(footFrozen));
                    telemetry.addData(selector + "lift: ", frozenCheck.apply(liftFrozen));
                    telemetry.addData(spacer + "elbow: ", frozenCheck.apply(elbowFrozen));
                } else if (position == 3) {
                    telemetry.addData(spacer + "foot: ", frozenCheck.apply(footFrozen));
                    telemetry.addData(spacer + "lift: ", frozenCheck.apply(liftFrozen));
                    telemetry.addData(selector + "elbow: ", frozenCheck.apply(elbowFrozen));
                }

                // if press a swap if its meant to be frozen or not
                if (!last.a && gamepad1.a) {
                    if (position == 1) {
                        footFrozen = !footFrozen;
                    } else if (position == 2) {
                        liftFrozen = !liftFrozen;
                    } else if (position == 3) {
                        elbowFrozen = !elbowFrozen;
                    }
                }

                // control motor selection
                if (!last.dpad_up && gamepad1.dpad_up) {
                    position++;
                    if (position > 3) {
                        position = 1;
                    }
                }
                if (!last.dpad_down && gamepad1.dpad_down) {
                    position++;
                    if (position < 1) {
                        position = 3;
                    }
                }

                // cycle modes
                if (!last.back && gamepad1.back) {
                    setModeManual();
                    break;
                }

                if (footFrozen) {
                    foot.setTargetPosition(foot.getCurrentPosition());
                } else {
                    foot.setPower(0);
                }
            }

            last = gamepad1;
            telemetry.update();
        }

    }

    void setModeManual() {
        position = 1;
        foot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRope.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        foot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRope.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRope.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftChain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        foot.setTargetPosition(foot.getCurrentPosition());
        liftRope.setTargetPosition(liftRope.getCurrentPosition());
        liftChain.setTargetPosition(liftChain.getCurrentPosition());
        elbow.setTargetPosition(elbow.getCurrentPosition());
        foot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRope.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        foot.setPower(1);
        liftRope.setPower(1);
        liftChain.setPower(1);
        elbow.setPower(1);
        currentMode = Mode.MANUAL;
    }

    void setModeFreezeMotor() {
        position = 1;
        foot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRope.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftChain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        foot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRope.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftChain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftRope.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftChain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        foot.setTargetPosition(foot.getCurrentPosition());
        liftRope.setTargetPosition(liftRope.getCurrentPosition());
        liftChain.setTargetPosition(liftChain.getCurrentPosition());
        elbow.setTargetPosition(elbow.getCurrentPosition());
        foot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRope.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftChain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbow.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        foot.setPower(1);
        liftRope.setPower(1);
        liftChain.setPower(1);
        elbow.setPower(1);
        footFrozen = true;
        liftFrozen = true;
        elbowFrozen = true;
        currentMode = Mode.FREEZE_MOTOR;
    }

    void setModeDirectional() {
        position = 1;
        foot.setPower(0);
        liftRope.setPower(0);
        liftChain.setPower(0);
        elbow.setPower(0);
        foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRope.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftChain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        foot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRope.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftChain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        currentMode = Mode.DIRECTIONAL;
    }
}
