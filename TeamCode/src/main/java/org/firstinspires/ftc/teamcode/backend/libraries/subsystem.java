package org.firstinspires.ftc.teamcode.backend.libraries;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class subsystem {
    HardwareMap hwMap;
    Telemetry telemetry;

    // hardware mapping must be done in run op mode so this allows us to easier control our objects

    public subsystem(Telemetry telemetry) { // for classes containing pre-hardwareMapped objects
        this(null, telemetry);
    }

    public subsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        hwMap = hardwareMap;
        telemetry = this.telemetry;
    }

    public Telemetry telemetry() {
        return this.telemetry;
    }
}
