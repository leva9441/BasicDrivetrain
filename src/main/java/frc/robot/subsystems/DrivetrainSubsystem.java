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

import frc.robot.MotorControllerGroup;

public class DrivetrainSubsystem extends SubsystemBase{
  
  //left motors
  CANSparkMax leftFrontMotor;
  CANSparkMax leftBackMotor;
  
  //right motors
  CANSparkMax rightFrontMotor;
  CANSparkMax rightBackMotor;

  //Differential drive is the class we use for all tank drive needs
  //This might need MotorController parameters but for now lets see if it works with two regular motors
  //which are being followed by two other motors.
  DifferentialDrive drivetrain;

  MotorControllerGroup leftMotorControllerGroup;
  MotorControllerGroup rightMotorControllerGroup;

  public DrivetrainSubsystem() {

    leftFrontMotor = new CANSparkMax(Constants.MotorConstants.leftFrontMotorID, MotorType.kBrushless);
    leftBackMotor = new CANSparkMax(Constants.MotorConstants.leftBackMotorID, MotorType.kBrushless);
  
    //right motors
    rightFrontMotor = new CANSparkMax(Constants.MotorConstants.rightFrontMotorID, MotorType.kBrushless);
    rightBackMotor = new CANSparkMax(Constants.MotorConstants.rightBackMotorID, MotorType.kBrushless);



    leftBackMotor.restoreFactoryDefaults();
    leftFrontMotor.restoreFactoryDefaults();
    rightBackMotor.restoreFactoryDefaults();
    rightFrontMotor.restoreFactoryDefaults();

    leftFrontMotor.getEncoder().setPosition(0);
    leftBackMotor.getEncoder().setPosition(0);
    rightFrontMotor.getEncoder().setPosition(0);
    rightBackMotor.getEncoder().setPosition(0);


    //leftBackMotor.follow(leftFrontMotor);
    //rightBackMotor.follow(rightFrontMotor);

    leftMotorControllerGroup = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotorControllerGroup = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

    drivetrain = new DifferentialDrive(leftMotorControllerGroup,rightMotorControllerGroup); 
    // new idea
    //what if I follow each motor in the parameters when I use followers

    //leftFrontMotor.setInverted(false);
    //leftBackMotor.setInverted(false);
    //rightFrontMotor.setInverted(true);
    //rightBackMotor.setInverted(true);


    drivetrain.setMaxOutput(Constants.MotorConstants.drivetrainMAXOutput);
  }

  public void arcadeDrive(double fwd, double rot, double op) {
    //leftBackMotor.follow(leftFrontMotor);
    //rightBackMotor.follow(rightFrontMotor);

    drivetrain.arcadeDrive(fwd, rot);    //IDEA: use tank drive not arcade

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
    SmartDashboard.putNumber("Front Left Encoder", leftFrontMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("Back Left Encoder", leftBackMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("Front Right Encoder", rightFrontMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("Back Right Encoder", rightBackMotor.getEncoder().getPosition());
    
    //Speed
    SmartDashboard.putNumber("Front Left Speed", leftFrontMotor.get());
    SmartDashboard.putNumber("Back Left Speed", leftBackMotor.get());
    SmartDashboard.putNumber("Front Right Speed", rightBackMotor.get());
    SmartDashboard.putNumber("Back Right Speed", rightBackMotor.get());

  }
}
