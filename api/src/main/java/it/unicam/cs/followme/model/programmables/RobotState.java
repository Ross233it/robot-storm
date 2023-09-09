package it.unicam.cs.followme.model.programmables;

import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;

public record RobotState(Integer robotId,
                         TwoDimensionalPoint position,
                         SpeedVector direction,
                         String label) {}



