package org.firstinspires.ftc.teamcode.opmodes.winterBreak;

import static java.lang.Math.PI;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.HashMap;

public class Constants {

    public enum RobotPositions {
        INITIAL, HOVERING_TO_GRAB, GRABBING, LOW_BASKET, HIGH_BASKET
    }

    public static boolean directionIsSetUp = true;
    public static DcMotorSimple.Direction
            footDirection = DcMotorSimple.Direction.FORWARD, // goes up
            liftRopeDirection = DcMotorSimple.Direction.FORWARD, // goes up
            liftChainDirection = DcMotorSimple.Direction.REVERSE, // goes down
            elbowDirection = DcMotorSimple.Direction.REVERSE, // goes out
            BLFRDrivetrainDirection = DcMotorSimple.Direction.FORWARD, // goes up
            BRFLDrivetrainDirection = DcMotorSimple.Direction.REVERSE; // goes up

    public static Integer
            elbowExtend = null,
            elbowRetract = 0,
            liftDown = 0,
            liftHover = null,
            liftMid = null,
            liftHigh = null,
            footDown = 0;

    public static Double
            wristGrab = 0.325,
            wristPlace = 0.2,
            wristFold = 0.85,
            wristExtend = 0.0,
            fingersOpen = 0.75,
            fingersClosed = 0.45,
            shoulderUp = 0.0,
            shoulderDown = 0.3;

    public static HashMap<RobotPositions, RobotPositionsData> positions = new HashMap<>();

    static {
        // the format is positions.put(RobotPositions.INSERT_NAME, new MotorPositions(INSERT_SHOULDER_POSITION, INSERT_ELBOW_POSITION));
        // INITIAL position have been filled in for you as a sample
        // please fill in HOVERING_TO_GRAB, GRABBING, LOW_BASKET, and HIGH_BASKET
        // if you want to make new ones be my guest, youll have to add the name of the position to line 13 then add a new positions.put()
        positions.put(RobotPositions.INITIAL, new RobotPositionsData(liftDown, wristFold, elbowRetract));
        positions.put(RobotPositions.HOVERING_TO_GRAB, new RobotPositionsData(liftHover, wristGrab, elbowExtend));
        positions.put(RobotPositions.GRABBING, new RobotPositionsData(liftDown, wristGrab, elbowExtend));
        positions.put(RobotPositions.LOW_BASKET, new RobotPositionsData(liftMid, wristPlace, elbowExtend));
        positions.put(RobotPositions.HIGH_BASKET, new RobotPositionsData(liftHigh, wristPlace, elbowExtend));

    }


    public static class RobotPositionsData {
        Integer lift, elbow;
        Double wrist;

        private RobotPositionsData(Integer lift, Double wris, Integer elbow) {
            this.lift = lift;
            this.wrist = wrist;
            this.elbow = elbow;
        }
    }

}
