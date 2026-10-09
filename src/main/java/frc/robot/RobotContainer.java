package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.TankDriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final CommandXboxController m_controller = new CommandXboxController(0);
  private final IntakeSubsystem motorIntakeSubsystem = new IntakeSubsystem();

  //Adjust speed constants for motors
  private static final double intake_Speed = 0.4;
  private static final double outtake_Speed = 0.4;

  // Create an instance of your drivetrain subsystem
  private final TankDriveSubsystem m_tankDrive = new TankDriveSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
  }

  /** Use this method to define your trigger->command mappings. */
  private void configureBindings() {
    m_controller.rightBumper()
            .whileTrue(Commands.startEnd(
                () -> motorIntakeSubsystem.runIntake(intake_Speed),
                () -> motorIntakeSubsystem.stop(),
                motorIntakeSubsystem
            ));

        // LB: Hold to Outtake (Eject)
        m_controller.leftBumper()
            .whileTrue(Commands.startEnd(
                () -> motorIntakeSubsystem.runOuttake(outtake_Speed),
                () -> motorIntakeSubsystem.stop(),
                motorIntakeSubsystem
            ));
  }

  /**
   * Passes the autonomous command to the main Robot class.
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Returns null because no autonomous routine has been defined yet
    return null; 
  }
}