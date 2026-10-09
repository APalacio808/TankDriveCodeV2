package frc.robot.subsystems;

//WPi Imports 
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//You get the imports from the WPILib library, which is this website: https://docs.wpilib.org/en/stable/docs/software/wpilib-overview/wpilib-overview.html

//Rev Imports
import com.revrobotics.spark.SparkMax; //This is the import for the SparkMax motor controller, which is used to control the motors on the robot.
import com.revrobotics.spark.SparkLowLevel.MotorType; //this is the import for the SparkMax motor controller, which is used to control the motors on the robot.
import com.revrobotics.spark.config.SparkMaxConfig; //This is the import for the SparkMax motor controller, which is used to control the motors on the robot.
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode; //this is the import for the SparkMax motor controller, which is used to control the motors on the robot.
//You get the imports from the Rev Robotics library, which is this website: https://www.revrobotics.com/sparkmax-software-overview/

//When importing or using 3rd party libraries, you always want to look at the documentation for the library to see what classes and methods are available to you. You can also look at the source code for the library if it is available, which can be helpful for understanding how the library works and how to use it.

public class TankDriveSubsystem extends SubsystemBase {
    //Speed Constant 
    private static final double speedMultiplier = 0.6;

    //Motors //Define is brushed or brusheless
    private final SparkMax leftLead = new SparkMax(1, MotorType.kBrushless);
    private final SparkMax leftFollow = new SparkMax(2, MotorType.kBrushless);
    private final SparkMax rightLead = new SparkMax(4, MotorType.kBrushless);
    private final SparkMax rightFollow = new SparkMax(14, MotorType.kBrushless);

    private final DifferentialDrive drive; //this is the differential drive object that will be used to control the robot's movement.
    private final XboxController xboxControl = new XboxController(0); //this is the xbox controller object that will be used to control the robot's movement.

    //Contstructor
    public TankDriveSubsystem() {
        //Configuration builders for left and right sides
        SparkMaxConfig leftLeadConfig = new SparkMaxConfig();
        SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
        SparkMaxConfig rightLeadConfig = new SparkMaxConfig();
        SparkMaxConfig rightFollowerConfig =   new SparkMaxConfig();
        //This is the configruation for the left and right sides of the robot.

        //Configuration for general motor behavior (Brake mode and for 40a Current Limiting)
        leftLeadConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40);
        leftFollowerConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40);
        rightLeadConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40);
        rightFollowerConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40);
        //This is the configuration for the general motor behavior. It sets the idle mode to brake and sets the smart current limit to 40 amps.

        //Configuration follower motors to follow lead motors
        leftFollowerConfig.follow(1); //Put the CAN Id for the left lead
        rightFollowerConfig.follow(4); //Put the CAN Id for the Right Lead
        //This is the configuration for the follower motors to follow the lead motors. It sets the left follower motor to follow the left lead motor and sets the right follower motor to follow the right lead motor.

        //Invert right side so pushing both stick make robot move forward
        leftLeadConfig.inverted(false); //This basically means that the left lead motor will not be inverted, so when you push the left stick forward, the left lead motor will spin forward.
        rightLeadConfig.inverted(true); //This basically means that the right lead motor will be inverted, so when you push the right stick forward, the right lead motor will spin backward.
        //This is for when you run the robot forward, it moves forward but when you turn the robot, it turns in the correct direction.

        //Applying Configuration Arrays to the robot and it persist parameters across power cycles
        leftLead.configure(leftLeadConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
        leftFollow.configure(leftFollowerConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
        rightLead.configure(rightLeadConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
        rightFollow.configure(rightFollowerConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);
        //This in explantion so it's easy means that the configuration for the left lead, left follower, right lead, and right follower motors will be applied to the robot and will persist across power cycles.
        //Power cycle means that when you turn the robot off and then back on, the configuration will still be there so it doesn't have to be reconfigured every time you turn the robot on.

        //Initalize Differential Drive for only lead motors
        drive = new DifferentialDrive(leftLead, rightLead);
        //you called drive earier in the code, and now you are initializing it with the left lead and right lead motors. This means that the differential drive will only control the lead motors, and the follower motors will follow the lead motors.
        //Different drive is a class that allows you to control the robot's movement using tank drive or arcade drive. It takes two motors as parameters, one for the left side and one for the right side.
    }   

    //Periodic Loop //This is where you binded the joysticks which will make the robot run, you don't have to put it in robot contaienr since it's looped to check every 20ms
    @Override
    public void periodic() {
        //Runs every 20ms automatically
        //WPILib controllers natively read up as Negative, so we invert them back with '-'
        drive.tankDrive(
            -xboxControl.getLeftY() * speedMultiplier, 
            -xboxControl.getRightY() * speedMultiplier
        );
    }
}




