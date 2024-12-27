package org.firstinspires.ftc.teamcode.opmodes.remoteDirections_deprecated_;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.HashMap;

public class Constants {

    final static double SHOULDER_BOOSTER = 8, ELBOW_BOOSTER = 8, FINGER_STEP = .05, WRIST_STEP = .05;

    public enum RobotPositions {
        INITIAL, HOVERING_TO_GRAB, GRABBING, LOW_BASKET, HIGH_BASKET
    }

    public static DcMotorSimple.Direction
            shoulderMotorLeftDirection = DcMotorSimple.Direction.FORWARD,
            shoulderMotorRightDirection = DcMotorSimple.Direction.REVERSE,
            elbowMotorDirection = DcMotorSimple.Direction.REVERSE;

    public static Integer
            shoulderOpen = null,
            elbowOpen = null;

    public static Double
            wristOpen = null,
            wristInitial = null,
            fingersGrab = null,
            fingersEject = null,
            wristGrab = null,
            wristPlace = null;

    public static HashMap<RobotPositions, RobotPositionsData> positions = new HashMap<>();

    static {
        // the format is positions.put(RobotPositions.INSERT_NAME, new MotorPositions(INSERT_SHOULDER_POSITION, INSERT_ELBOW_POSITION));
        // INITIAL position have been filled in for you as a sample
        // please fill in HOVERING_TO_GRAB, GRABBING, LOW_BASKET, and HIGH_BASKET
        // if you want to make new ones be my guest, youll have to add the name of the position to line 13 then add a new positions.put()
        positions.put(RobotPositions.INITIAL, new RobotPositionsData(0, 0));
        positions.put(RobotPositions.HOVERING_TO_GRAB, new RobotPositionsData(null, null));
        positions.put(RobotPositions.GRABBING, new RobotPositionsData(null, null));
        positions.put(RobotPositions.LOW_BASKET, new RobotPositionsData(null, null));
        positions.put(RobotPositions.HIGH_BASKET, new RobotPositionsData(null, null));
    }


    public static class RobotPositionsData {
        Integer shoulder, elbow;

        private RobotPositionsData(Integer shoulder, Integer elbow) {
            this.shoulder = shoulder;
            this.elbow = elbow;
        }
    }
}
