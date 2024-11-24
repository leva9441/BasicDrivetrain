// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
  
  //left motors
  CANSparkMax leftFrontMotor = new CANSparkMax(Constants.MotorConstants.leftFrontMotorID, MotorType.kBrushless);
  CANSparkMax leftBackMotor = new CANSparkMax(Constants.MotorConstants.leftBackMotorID, MotorType.kBrushless);
  
  //right motors
  CANSparkMax rightFrontMotor = new CANSparkMax(Constants.MotorConstants.rightFrontMotorID, MotorType.kBrushless);
  CANSparkMax rightBackMotor = new CANSparkMax(Constants.MotorConstants.rightBackMotorID, MotorType.kBrushless);

  //encoders
  RelativeEncoder leftRelativeEncoder = leftFrontMotor.getEncoder();
  RelativeEncoder rightRelativeEncoder = rightFrontMotor.getEncoder();

  //Differential drive is the class we use for all tank drive needs
  //This might need MotorController parameters but for now lets see if it works with two regular motors
  //which are being followed by two other motors.
  DifferentialDrive drivetrain = new DifferentialDrive(leftFrontMotor,rightFrontMotor);

  /** Creates a new ExampleSubsystem. */
  public DrivetrainSubsystem() {

    leftBackMotor.restoreFactoryDefaults();
    leftFrontMotor.restoreFactoryDefaults();
    rightBackMotor.restoreFactoryDefaults();
    rightFrontMotor.restoreFactoryDefaults();

    leftRelativeEncoder.setPosition(0);
    rightRelativeEncoder.setPosition(0);

    leftBackMotor.follow(leftFrontMotor);
    rightBackMotor.follow(rightFrontMotor);

    //I don't know if I have to invert the following motors as well, but I'm going to
    // because the video inverted the entire MotorControllerGroup.
    leftFrontMotor.setInverted(false);
    leftBackMotor.setInverted(false);
    rightFrontMotor.setInverted(true);
    rightBackMotor.setInverted(true);
  }

  public void arcadeDrive(double fwd, double rot) {
    drivetrain.arcadeDrive(fwd, rot);
  }

  public void stopMotors() {
    leftFrontMotor.set(0);
    rightFrontMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
