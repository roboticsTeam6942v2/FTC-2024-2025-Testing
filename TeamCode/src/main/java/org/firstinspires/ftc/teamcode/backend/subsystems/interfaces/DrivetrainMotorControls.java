package org.firstinspires.ftc.teamcode.backend.subsystems.interfaces;

import androidx.annotation.NonNull;

public interface DrivetrainMotorControls {

    enum DTMotors {
        fl, fr, bl, br, f, b, l, r, dt, m, all
    }

    /**
     * Returns whether or not the drivetrain is busy
     * @return isBusy (true or false)
     */
    boolean isBusy();

    /**
     * Set power to motors using a case switch
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param p Power (between -1 and 1)
     */
    void SP(@NonNull DTMotors m, double p);

    /**
     * Sets the mode of the motor to RUN_TO_POSITION using case switch
     *
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt, all)
     */
    void RTP(@NonNull DTMotors m);

    /**
     * Set the target position of the motors using a case switch
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     * @param tp Target Position in ticks
     */
    void STP(@NonNull DTMotors m, int tp);

    /**
     * Sets the mode of the motor to STOP_AND_RESET_ENCODERS using case switch
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void SAR(@NonNull DTMotors m);

    /**
     * Sets the mode of the motor to RUN_WITHOUT_ENCODERS using case switch
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void RWE(@NonNull DTMotors m);

    /**
     * Sets the mode of the motor to RUN_USING_ENCODERS using case switch
     * @param m Motor abbreviation (fl, fr, bl, br, f, b, l, r, dt)
     */
    void RUE(@NonNull DTMotors m);
}
