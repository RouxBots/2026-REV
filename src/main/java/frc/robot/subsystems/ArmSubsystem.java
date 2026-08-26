// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.IntakeSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.ConveyorSetpoints;
import frc.robot.Constants.IntakeSubsystemConstants.IntakeSetpoints;

public class ArmSubsystem extends SubsystemBase {
  // Initialize intake SPARK. We will use open loop control for this.
  private SparkFlex armMotor =
      new SparkFlex(IntakeSubsystemConstants.kIntakeMotorCanId, MotorType.kBrushless);
      //before deploying code to robot need to update constants with arm motor can ID


  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {
    /*
     * Apply the appropriate configurations to the SPARKs.
     *
     * kResetSafeParameters is used to get the SPARK to a known state. This
     * is useful in case the SPARK is replaced.
     *
     * kPersistParameters is used to ensure the configuration is not lost when
     * the SPARK loses power. This is useful for power cycles that may occur
     * mid-operation.
     */
    armMotor.configure(
        Configs.ArmSubsystem.intakeConfig, 
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
//may need to update intakeConfig


    System.out.println("---> ArmSubsystem initialized");
  }

  /** Set the arm motor power in the range of [-1, 1]. */
  private void setArmPower(double power) {
    armMotor.set(power);
  }

  private void lowerArm() {
    armMotor.set(0.3);  // positive = down (adjust if direction is reversed)
}

private void raiseArm() {
    armMotor.set(-0.3); // negative = up (adjust if direction is reversed)
}

private void stopArm() {
    armMotor.set(0.0); // stop motor
}

public Command lowerArmCommand() {
    return this.startEnd(
        () -> lowerArm(),   // run while button held
        () -> stopArm()     // stop when button released
    ).withName("Arm Down");
}

public Command raiseArmCommand() {
    return this.startEnd(
        () -> raiseArm(),
        () -> stopArm()
    ).withName("Arm Up");
}


  @Override
  public void periodic() {
    // Display subsystem values
    SmartDashboard.putNumber("Arm | Arm | Applied Output", armMotor.getAppliedOutput());
  }

}
