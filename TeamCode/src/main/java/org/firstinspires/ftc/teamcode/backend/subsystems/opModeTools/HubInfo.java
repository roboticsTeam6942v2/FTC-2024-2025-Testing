package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.lynx.commands.standard.LynxSetModuleLEDColorCommand;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class HubInfo {
    private final LynxModule lynxModule;

    public HubInfo(LynxModule lynxModule) {
        this.lynxModule = lynxModule;
    }

    public double getVoltage() {
        return this.lynxModule.getInputVoltage(VoltageUnit.VOLTS);
    }

    public double getCurrent() {
        return this.lynxModule.getCurrent(CurrentUnit.AMPS);
    }

    public double getTemperature() {
        return this.lynxModule.getTemperature(TempUnit.FARENHEIT);
    }

    public void setLed(byte r, byte g, byte b) {
        while (true) {
            try {
                new LynxSetModuleLEDColorCommand(lynxModule, r, g, b).send();
            } catch (Exception e) {

            }
        }
    }
}
