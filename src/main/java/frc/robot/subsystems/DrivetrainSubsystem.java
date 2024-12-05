// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
  
  //left motors
  CANSparkMax leftFrontMotor;
  CANSparkMax leftBackMotor;
  
  //right motors
  CANSparkMax rightFrontMotor;
  CANSparkMax rightBackMotor;

  //encoders
  RelativeEncoder leftFrontRelativeEncoder;
  RelativeEncoder leftBackRelativeEncoder;
  RelativeEncoder rightFrontRelativeEncoder;
  RelativeEncoder rightBackRelativeEncoder;

  //Differential drive is the class we use for all tank drive needs
  //This might need MotorController parameters but for now lets see if it works with two regular motors
  //which are being followed by two other motors.
  DifferentialDrive drivetrain;

  /** Creates a new ExampleSubsystem. */
  public DrivetrainSubsystem() {
    drivetrain = new DifferentialDrive(leftFrontMotor,rightFrontMotor);

  leftFrontMotor = new CANSparkMax(Constants.MotorConstants.leftFrontMotorID, MotorType.kBrushless);
  leftBackMotor = new CANSparkMax(Constants.MotorConstants.leftBackMotorID, MotorType.kBrushless);
  
  //right motors
  rightFrontMotor = new CANSparkMax(Constants.MotorConstants.rightFrontMotorID, MotorType.kBrushless);
  rightBackMotor = new CANSparkMax(Constants.MotorConstants.rightBackMotorID, MotorType.kBrushless);

  //encoders
  leftFrontRelativeEncoder = leftFrontMotor.getEncoder();
  leftBackRelativeEncoder = leftBackMotor.getEncoder();
  rightFrontRelativeEncoder = rightFrontMotor.getEncoder();
  rightBackRelativeEncoder = rightBackMotor.getEncoder();


    leftBackMotor.restoreFactoryDefaults();
    leftFrontMotor.restoreFactoryDefaults();
    rightBackMotor.restoreFactoryDefaults();
    rightFrontMotor.restoreFactoryDefaults();

    leftFrontRelativeEncoder.setPosition(0);
    leftBackRelativeEncoder.setPosition(0);
    rightFrontRelativeEncoder.setPosition(0);
    rightBackRelativeEncoder.setPosition(0);


    leftBackMotor.follow(leftFrontMotor);
    rightBackMotor.follow(rightFrontMotor);


    //I don't know if I have to invert the following motors as well, but I'm going to
    // because the video inverted the entire MotorControllerGroup.
    leftFrontMotor.setInverted(false);
    leftBackMotor.setInverted(true);//test to get it to go at all
    rightFrontMotor.setInverted(true);
    rightBackMotor.setInverted(true);


    drivetrain.setMaxOutput(0.85);
  }

  public void arcadeDrive(double fwd, double rot, double op) {
    leftBackMotor.follow(leftFrontMotor);
    rightBackMotor.follow(rightFrontMotor);

    drivetrain.arcadeDrive(fwd, rot);

    SmartDashboard.putNumber("Forward Input", fwd);
    SmartDashboard.putNumber("Turning Input", rot);
    SmartDashboard.putNumber("Operating Power %", op);
  }

  public void stopMotors() {
    leftFrontMotor.set(0);
    rightFrontMotor.set(0);
  }

  @Override
  public void periodic() {
    //Encoders
    SmartDashboard.putNumber("Front Left Encoder", leftFrontRelativeEncoder.getPosition());
    SmartDashboard.putNumber("Back Left Encoder", leftBackRelativeEncoder.getPosition());
    SmartDashboard.putNumber("Front Right Encoder", rightFrontRelativeEncoder.getPosition());
    SmartDashboard.putNumber("Back Right Encoder", rightBackRelativeEncoder.getPosition());
    
    //Speed
    SmartDashboard.putNumber("Front Left Speed", leftFrontMotor.get());
    SmartDashboard.putNumber("Back Left Speed", leftBackMotor.get());
    SmartDashboard.putNumber("Front Right Speed", rightBackMotor.get());
    SmartDashboard.putNumber("Back Right Speed", rightBackMotor.get());

  }
}
