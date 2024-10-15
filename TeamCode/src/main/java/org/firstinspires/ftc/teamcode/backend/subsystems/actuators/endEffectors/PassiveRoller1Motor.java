package org.firstinspires.ftc.teamcode.backend.subsystems.actuators.endEffectors;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.backend.libraries.subsystem;
import org.firstinspires.ftc.teamcode.backend.subsystems.actuators.base.Motor;

public class PassiveRoller1Motor extends subsystem {
    private Motor motor;
    public final String name;
    private final double rollerSpeed;

    enum RollerControl{
        FORWARD, BACKWARD, DISENGAGE
    }

    public PassiveRoller1Motor(String name, Motor motor, Telemetry telemetry, int rollerSpeed) {
        super(telemetry);
        this.name = name;
        this.motor = motor;
        this.rollerSpeed = rollerSpeed;
        motor.RWE();
    }

    public void roll (RollerControl control){
        switch (control) {
            case FORWARD:
                motor.SP(rollerSpeed);
                break;
            case BACKWARD:
                motor.SP(-rollerSpeed);
                break;
            case DISENGAGE:
                motor.SP(0);
                break;
        }
    }
}
