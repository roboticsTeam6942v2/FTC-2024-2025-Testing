package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import static java.lang.Math.PI;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.HashMap;

public class Constants {

    final static double SHOULDER_BOOSTER = 8, ELBOW_BOOSTER = 8, FINGER_STEP = .05, WRIST_STEP = .05;

    public enum RobotPositions {
        INITIAL, HOVERING_TO_GRAB, GRABBING, LOW_BASKET, HIGH_BASKET
    }

    public static boolean directionIsSetUp = false;
    public static DcMotorSimple.Direction
            footDirection = DcMotorSimple.Direction.FORWARD,
            liftRopeDirection = DcMotorSimple.Direction.FORWARD,
            liftChainDirection = DcMotorSimple.Direction.FORWARD,
            elbowDirection = DcMotorSimple.Direction.FORWARD;

    public static Integer
            elbowExtend = null,
            elbowRetract = null,
            liftLow = null,
            liftHover = null,
            liftMid = null,
            liftHigh = null,
            footDown = null;

    public static Double
            wristGrab = null,
            wristPlace = null,
            fingersOpen = null,
            fingersClosed = null,
            shoulderUp = null,
            shoulderDown = null;

    public static HashMap<RobotPositions, RobotPositionsData> positions = new HashMap<>();

    static {
        // the format is positions.put(RobotPositions.INSERT_NAME, new MotorPositions(INSERT_SHOULDER_POSITION, INSERT_ELBOW_POSITION));
        // INITIAL position have been filled in for you as a sample
        // please fill in HOVERING_TO_GRAB, GRABBING, LOW_BASKET, and HIGH_BASKET
        // if you want to make new ones be my guest, youll have to add the name of the position to line 13 then add a new positions.put()
        positions.put(RobotPositions.INITIAL, new RobotPositionsData(0, null, null));
        positions.put(RobotPositions.HOVERING_TO_GRAB, new RobotPositionsData(liftHover, wristGrab, shoulderDown));

    }


    public static class RobotPositionsData {
        Integer lift;
        Double wrist, shoulder;

        private RobotPositionsData(Integer lift, Double wrist, Double shoulder) {
            this.lift = lift;
            this.wrist = wrist;
            this.shoulder = shoulder;
        }
    }

}
