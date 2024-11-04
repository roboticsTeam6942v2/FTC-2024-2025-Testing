package org.firstinspires.ftc.teamcode.backend.subsystems.interfaces;

public interface DrivetrainMotorControls {

    enum DTMotors {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT, FRONT, BACK, LEFT, RIGHT, DRIVETRAIN_BASIC_4, MIDSHIFT, ALL
    }

    /**
     * Returns whether or not the drivetrain is busy
     *
     * @return isBusy (true or false)
     */
    boolean isBusy();

    /**
     * Set power to motors using a case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param power  Power (between -1 and 1)
     */
    void setPower(DTMotors motors, double power);

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    void runToPosition(DTMotors motors);

    /**
     * Set the target position of the motors using a case switch
     *
     * @param motors         Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param targetPosition Target Position in ticks
     */
    void setTargetPosition(DTMotors motors, int targetPosition);

    /**
     * Sets the mode of the motor to STOP_AND_RESET_ENCODER using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void stopAndReset(DTMotors motors);

    /**
     * Sets the mode of the motor to RUN_WITHOUT_ENCODER using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void runWithoutEncoder(DTMotors motors);

    /**
     * Sets the mode of the motor to RUN_USING_ENCODER using case switch
     *
     * @param motors Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void runUsingEncoder(DTMotors motors);
}
