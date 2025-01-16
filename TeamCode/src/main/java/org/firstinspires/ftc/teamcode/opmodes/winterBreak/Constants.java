package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Constants {

    public enum RobotPositions {
        INITIAL, GROUND, GRABBING, LOW_BASKET, HIGH_BASKET, LOW_BAR, HIGH_BAR
    }

    public static boolean directionIsSetUp = true;
    public static DcMotorSimple.Direction
            footDirection = DcMotorSimple.Direction.FORWARD, // goes up
            liftRopeDirection = DcMotorSimple.Direction.REVERSE, // goes up
            liftChainDirection = DcMotorSimple.Direction.FORWARD, // goes up
            elbowDirection = DcMotorSimple.Direction.REVERSE, // goes out
            BLFRDrivetrainDirection = DcMotorSimple.Direction.FORWARD, // goes up
            BRFLDrivetrainDirection = DcMotorSimple.Direction.REVERSE; // goes up

    public static Integer
            elbowExtend = 4500,
            elbowRetract = 0,
            liftDown = 0,
            liftGrabbing = 300,
            liftLowBar = 2000, // go down 1000
            liftHighBar = null,
            liftLowBasket = 5750,
            liftHighBasket = null;

    public static Double
            wristGrab = 0.325,
            wristPlace = 0.0, // not in use until servo shoulders work, 0.2 will be the 45 deg
            wristFold = 0.85,
            fingersOpen = 0.75,
            fingersClosed = 0.45,
            shoulderUp = 0.0,
            shoulderDown = 0.3;
}
