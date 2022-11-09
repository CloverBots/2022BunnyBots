package frc.robot.subsystems;

import frc.robot.LiftPosition;

public interface LiftObserver {
  /**
   * Gets the current lift position.
   * 
   * @return The current lift position.
   */
  LiftPosition getLiftPosition();
}
