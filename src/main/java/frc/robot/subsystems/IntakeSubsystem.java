package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    //Intake Motor 
    private final TalonFX intakeMotor = new TalonFX(12);
    
    //Control Request Object for Precent output (-1.0 to 1.0)
    private final DutyCycleOut motorDutyCycle = new DutyCycleOut(0);

    public void runIntake(double speed) {
        intakeMotor.setControl(motorDutyCycle.withOutput(speed));
    }
    
      public void runOuttake(double speed) {
        intakeMotor.setControl(motorDutyCycle.withOutput(-speed));
    }

    public void stop() {
        intakeMotor.setControl(motorDutyCycle.withOutput(0));
    }
}